package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BharatRepository
import com.example.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen(val title: String) {
    HOME("Services & Workers"),
    TRANSIT("Cab & Bus"),
    SCHEMES("Sarkari Yojanas"),
    WALLET("Bharat Wallet"),
    VERIFICATION("Verification"),
    SUPPORT("Telegram Support"),
    ADMIN("Admin Portal")
}

class BharatViewModel(
    val repository: BharatRepository = BharatRepository()
) : ViewModel() {

    // Current Active Tab/Screen
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Screen back stack for predictive back handler
    private val screenStack = mutableListOf(AppScreen.HOME)

    // User Location
    private val _currentLocation = MutableStateFlow("Varanasi, UP (221001)")
    val currentLocation: StateFlow<String> = _currentLocation.asStateFlow()

    // Search and Filters
    private val _workerSearchQuery = MutableStateFlow("")
    val workerSearchQuery: StateFlow<String> = _workerSearchQuery.asStateFlow()

    private val _selectedTrade = MutableStateFlow<WorkerTrade?>(null)
    val selectedTrade: StateFlow<WorkerTrade?> = _selectedTrade.asStateFlow()

    private val _selectedBusinessCategory = MutableStateFlow<BusinessCategory?>(null)
    val selectedBusinessCategory: StateFlow<BusinessCategory?> = _selectedBusinessCategory.asStateFlow()

    private val _selectedSchemeCategory = MutableStateFlow(SchemeCategory.ALL)
    val selectedSchemeCategory: StateFlow<SchemeCategory> = _selectedSchemeCategory.asStateFlow()

    private val _schemeSearchQuery = MutableStateFlow("")
    val schemeSearchQuery: StateFlow<String> = _schemeSearchQuery.asStateFlow()

    // Data from repo
    val userProfile = repository.userProfile
    val walletBalance = repository.walletBalance
    val transactions = repository.transactions
    val bookings = repository.bookings
    val transitBookings = repository.transitBookings
    val schemes = repository.schemes
    val formRequests = repository.formRequests
    val verifications = repository.verifications
    val platformEarnings = repository.platformEarnings
    val isAdminLoggedIn = repository.isAdminLoggedIn

    // Filtered Workers
    val filteredWorkers: StateFlow<List<SkillWorker>> = combine(
        repository.workers,
        _workerSearchQuery,
        _selectedTrade
    ) { workers, query, trade ->
        workers.filter { worker ->
            val matchesTrade = trade == null || worker.trade == trade
            val matchesQuery = query.isBlank() ||
                worker.name.contains(query, ignoreCase = true) ||
                worker.trade.displayName.contains(query, ignoreCase = true) ||
                worker.trade.hindiName.contains(query, ignoreCase = true) ||
                worker.villageOrArea.contains(query, ignoreCase = true) ||
                worker.specialSkills.any { it.contains(query, ignoreCase = true) }
            matchesTrade && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Businesses
    val filteredBusinesses: StateFlow<List<LocalBusiness>> = combine(
        repository.businesses,
        _workerSearchQuery,
        _selectedBusinessCategory
    ) { businesses, query, cat ->
        businesses.filter { biz ->
            val matchesCat = cat == null || biz.category == cat
            val matchesQuery = query.isBlank() ||
                biz.name.contains(query, ignoreCase = true) ||
                biz.category.displayName.contains(query, ignoreCase = true) ||
                biz.address.contains(query, ignoreCase = true)
            matchesCat && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Schemes
    val filteredSchemes: StateFlow<List<GovernmentScheme>> = combine(
        repository.schemes,
        _schemeSearchQuery,
        _selectedSchemeCategory
    ) { schemeList, query, cat ->
        schemeList.filter { scheme ->
            val matchesCat = cat == SchemeCategory.ALL || scheme.category == cat
            val matchesQuery = query.isBlank() ||
                scheme.title.contains(query, ignoreCase = true) ||
                scheme.hindiTitle.contains(query, ignoreCase = true) ||
                scheme.ministry.contains(query, ignoreCase = true) ||
                scheme.eligibility.contains(query, ignoreCase = true)
            matchesCat && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            screenStack.add(screen)
            _currentScreen.value = screen
        }
    }

    fun handleBackPress(): Boolean {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = screenStack.last()
            return true
        } else if (_currentScreen.value != AppScreen.HOME) {
            _currentScreen.value = AppScreen.HOME
            screenStack.clear()
            screenStack.add(AppScreen.HOME)
            return true
        }
        return false
    }

    fun setLocation(newLoc: String) {
        _currentLocation.value = newLoc
    }

    fun setWorkerSearch(q: String) {
        _workerSearchQuery.value = q
    }

    fun selectTrade(trade: WorkerTrade?) {
        _selectedTrade.value = if (_selectedTrade.value == trade) null else trade
    }

    fun selectBusinessCategory(cat: BusinessCategory?) {
        _selectedBusinessCategory.value = if (_selectedBusinessCategory.value == cat) null else cat
    }

    fun setSchemeSearch(q: String) {
        _schemeSearchQuery.value = q
    }

    fun selectSchemeCategory(cat: SchemeCategory) {
        _selectedSchemeCategory.value = cat
    }

    // Booking a worker
    fun bookService(
        worker: SkillWorker,
        date: String,
        timeSlot: String,
        address: String,
        description: String,
        payViaWallet: Boolean,
        onSuccess: (ServiceBooking) -> Unit,
        onError: (String) -> Unit
    ) {
        val total = worker.hourlyOrVisitRate + (worker.hourlyOrVisitRate * repository.platformEarnings.value.defaultCommissionPercent) / 100
        if (payViaWallet && walletBalance.value < total) {
            onError("Insufficient Bharat Wallet balance (₹${walletBalance.value}). Please recharge or choose Cash on Visit.")
            return
        }
        val booking = repository.createServiceBooking(
            worker = worker,
            date = date,
            timeSlot = timeSlot,
            address = address,
            description = description,
            payViaWallet = payViaWallet
        )
        onSuccess(booking)
    }

    // Booking Transit (Cab / Bus)
    fun bookTransit(
        type: TransitType,
        fromLoc: String,
        toLoc: String,
        fare: Int,
        operatorName: String,
        vehicleNo: String,
        seat: String? = null,
        payViaWallet: Boolean,
        onSuccess: (TransitBooking) -> Unit,
        onError: (String) -> Unit
    ) {
        if (payViaWallet && walletBalance.value < fare) {
            onError("Insufficient Bharat Wallet balance (₹${walletBalance.value}). Please recharge wallet.")
            return
        }
        val booking = repository.bookTransitRide(
            type = type,
            fromLoc = fromLoc,
            toLoc = toLoc,
            fare = fare,
            operatorName = operatorName,
            vehicleNo = vehicleNo,
            seat = seat,
            payViaWallet = payViaWallet
        )
        onSuccess(booking)
    }

    // Form Assistance Request submission
    fun submitSchemeFormAssistance(
        scheme: GovernmentScheme,
        name: String,
        phone: String,
        address: String,
        notes: String,
        onSuccess: (FormAssistanceRequest) -> Unit
    ) {
        val req = repository.submitFormAssistanceRequest(
            schemeId = scheme.id,
            schemeTitle = scheme.title,
            name = name,
            phone = phone,
            address = address,
            notes = notes
        )
        onSuccess(req)
    }

    // User KYC verification submission
    fun submitVerification(
        name: String,
        phone: String,
        role: String,
        trade: String,
        docType: String,
        docNumber: String,
        onSuccess: (VerificationSubmission) -> Unit
    ) {
        val sub = repository.submitVerificationRequest(
            name = name,
            phone = phone,
            roleType = role,
            trade = trade,
            docType = docType,
            docNumber = docNumber
        )
        onSuccess(sub)
    }

    // Add Money
    fun addMoney(amount: Int) {
        repository.addMoneyToWallet(amount)
    }

    // Admin Authentication
    fun loginAdmin(username: String, pass: String): Boolean {
        return repository.verifyAdminLogin(username, pass)
    }

    fun logoutAdmin() {
        repository.adminLogout()
    }

    fun approveVerification(verId: String, approve: Boolean) {
        repository.updateVerificationStatus(verId, approve)
    }

    fun updateBookingStatus(bookingId: String, status: BookingStatus) {
        repository.updateBookingStatus(bookingId, status)
    }

    fun updateFormStatus(reqId: String, status: String) {
        repository.updateFormRequestStatus(reqId, status)
    }

    fun updatePlatformCommission(pct: Int) {
        repository.updatePlatformCommissionPercent(pct)
    }

    // Launch Telegram Support
    fun openTelegramSupport(context: Context) {
        try {
            val telegramIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/ArjunRajput04"))
            telegramIntent.setPackage("org.telegram.messenger")
            context.startActivity(telegramIntent)
        } catch (e: Exception) {
            // Fallback to web browser
            try {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/ArjunRajput04"))
                context.startActivity(webIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "Telegram support: @ArjunRajput04", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Make Phone Call intent
    fun dialPhone(context: Context, phoneNumber: String) {
        try {
            val cleanPhone = phoneNumber.filter { it.isDigit() || it == '+' }
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanPhone"))
            context.startActivity(dialIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Contact: $phoneNumber", Toast.LENGTH_SHORT).show()
        }
    }
}
