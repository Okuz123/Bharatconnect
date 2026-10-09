package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.AppScreen
import com.example.ui.BharatViewModel
import com.example.ui.components.SupportBanner
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val workers by viewModel.filteredWorkers.collectAsState()
    val businesses by viewModel.filteredBusinesses.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val selectedTrade by viewModel.selectedTrade.collectAsState()
    val searchQuery by viewModel.workerSearchQuery.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Workers, 1: Local Shops, 2: My Bookings
    var selectedWorkerForBooking by remember { mutableStateOf<SkillWorker?>(null) }
    var showRegisterWorkerDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BharatBgLight)
    ) {
        // Sticky Header / Search Section
        Surface(
            color = BharatNavy,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setWorkerSearch(it) },
                    placeholder = {
                        Text(
                            "Search Electrician, Plumber, Kirana, Welder...",
                            color = Color(0xFFA0AEC0),
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = BharatSaffron
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setWorkerSearch("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.White)
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Color(0x2EFFFFFF),
                        unfocusedContainerColor = Color(0x1AFFFFFF),
                        focusedBorderColor = BharatSaffron,
                        unfocusedBorderColor = Color(0x44FFFFFF)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_search_input")
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Customer Support Banner Highlight
            item {
                Box(modifier = Modifier.padding(16.dp)) {
                    SupportBanner(viewModel = viewModel, compact = true)
                }
            }

            // Quick Category Strip
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Find Local Skills & Shops",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = BharatNavy
                        )
                        TextButton(
                            onClick = { showRegisterWorkerDialog = true },
                            modifier = Modifier.testTag("register_worker_button")
                        ) {
                            Icon(
                                Icons.Default.AddCircle,
                                contentDescription = null,
                                tint = BharatSaffron,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Register Trade",
                                color = BharatSaffron,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Trade Category Filter Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            FilterChip(
                                selected = selectedTrade == null,
                                onClick = { viewModel.selectTrade(null) },
                                label = { Text("All (सभी)") },
                                leadingIcon = {
                                    Icon(Icons.Default.Apps, contentDescription = null, modifier = Modifier.size(16.dp))
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = BharatNavy,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }

                        items(WorkerTrade.values()) { trade ->
                            val isSelected = selectedTrade == trade
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectTrade(trade) },
                                label = {
                                    Text("${trade.displayName}")
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = BharatNavy,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Section Tabs: Skilled Workers | Local Shops | My Bookings
            item {
                Spacer(modifier = Modifier.height(14.dp))
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = BharatSurface,
                    contentColor = BharatNavy,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                "Skilled Artisans (${workers.size})",
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                "Local Shops (${businesses.size})",
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = {
                            Text(
                                "My Bookings (${bookings.size})",
                                fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Content based on Tab
            when (selectedTab) {
                0 -> {
                    // Skilled Workers List
                    if (workers.isEmpty()) {
                        item {
                            EmptyListPlaceholder(
                                message = "No workers found for this skill in your area.",
                                onAction = { viewModel.selectTrade(null); viewModel.setWorkerSearch("") }
                            )
                        }
                    } else {
                        items(workers) { worker ->
                            WorkerCard(
                                worker = worker,
                                onCall = { viewModel.dialPhone(context, worker.phone) },
                                onBook = { selectedWorkerForBooking = worker },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                1 -> {
                    // Local Businesses
                    items(businesses) { business ->
                        BusinessCard(
                            business = business,
                            onCall = { viewModel.dialPhone(context, business.phone) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }

                2 -> {
                    // My Bookings
                    if (bookings.isEmpty()) {
                        item {
                            EmptyListPlaceholder(
                                message = "You haven't made any service bookings yet.",
                                onAction = { selectedTab = 0 }
                            )
                        }
                    } else {
                        items(bookings) { booking ->
                            BookingItemCard(
                                booking = booking,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Service Booking Modal Dialog
    if (selectedWorkerForBooking != null) {
        val worker = selectedWorkerForBooking!!
        ServiceBookingDialog(
            worker = worker,
            viewModel = viewModel,
            onDismiss = { selectedWorkerForBooking = null },
            onConfirmed = { booking ->
                selectedWorkerForBooking = null
                selectedTab = 2 // Switch to My Bookings
                Toast.makeText(context, "Booking Confirmed! ID: ${booking.id}", Toast.LENGTH_LONG).show()
            }
        )
    }

    // Register Worker / Artisan Dialog
    if (showRegisterWorkerDialog) {
        RegisterWorkerDialog(
            viewModel = viewModel,
            onDismiss = { showRegisterWorkerDialog = false },
            onRegistered = {
                showRegisterWorkerDialog = false
                Toast.makeText(context, "Registration submitted for verification!", Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Composable
fun WorkerCard(
    worker: SkillWorker,
    onCall: () -> Unit,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("worker_card_${worker.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Avatar with trade icon
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BharatNavyLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (worker.trade) {
                            WorkerTrade.ELECTRICIAN -> Icons.Default.Bolt
                            WorkerTrade.PLUMBER -> Icons.Default.WaterDrop
                            WorkerTrade.CARPENTER -> Icons.Default.Handyman
                            WorkerTrade.MASON -> Icons.Default.HomeRepairService
                            WorkerTrade.BLACKSMITH -> Icons.Default.Hardware
                            WorkerTrade.AC_TECHNICIAN -> Icons.Default.AcUnit
                            WorkerTrade.PANDIT_JI -> Icons.Default.Celebration
                            WorkerTrade.DRIVER -> Icons.Default.DirectionsCar
                            WorkerTrade.TAILOR -> Icons.Default.ContentCut
                            else -> Icons.Default.Build
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = worker.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = BharatTextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        // Availability Tag
                        Surface(
                            color = if (worker.isAvailable) BharatEmeraldContainer else Color(0xFFFFEBEE),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (worker.isAvailable) "Available" else "Busy",
                                color = if (worker.isAvailable) BharatEmerald else BharatError,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${worker.trade.displayName} (${worker.trade.hindiName})",
                        color = BharatSaffron,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Location & Experience
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Place,
                            contentDescription = null,
                            tint = BharatTextSecondary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${worker.villageOrArea}, ${worker.city}",
                            color = BharatTextSecondary,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "• ${worker.experienceYears} yrs exp",
                            color = BharatTextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Badges row: Aadhaar / Vishwakarma / Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Rating
                Surface(
                    color = BharatGoldContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFF57F17),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${worker.rating} (${worker.reviewsCount})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF825800)
                        )
                    }
                }

                // PM Vishwakarma badge
                if (worker.vishwakarmaBeneficiary) {
                    Surface(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                Icons.Default.Verified,
                                contentDescription = null,
                                tint = BharatEmerald,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "PM Vishwakarma",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BharatEmerald
                            )
                        }
                    }
                }

                // Verified Badge
                Surface(
                    color = Color(0xFFE3F2FD),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = worker.verificationTier.badgeTitle,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BharatNavy,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            if (worker.specialSkills.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Skills: " + worker.specialSkills.joinToString(", "),
                    fontSize = 11.sp,
                    color = BharatTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = BharatBorder, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Pricing & Booking Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Visiting / Service Fee",
                        fontSize = 10.sp,
                        color = BharatTextSecondary
                    )
                    Text(
                        text = "₹${worker.hourlyOrVisitRate}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BharatNavy
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onCall,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("call_worker_${worker.id}")
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "Call",
                            tint = BharatNavy,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call", color = BharatNavy, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onBook,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BharatSaffron),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("book_worker_${worker.id}")
                    ) {
                        Icon(
                            Icons.Default.EventAvailable,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Book Now", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard(
    business: LocalBusiness,
    onCall: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = business.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BharatTextPrimary
                    )
                    Text(
                        text = business.category.displayName,
                        fontSize = 12.sp,
                        color = BharatSaffron,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Owner: ${business.ownerName} • ${business.address}",
                        fontSize = 11.sp,
                        color = BharatTextSecondary
                    )
                }

                Surface(
                    color = BharatGoldContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFF57F17),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${business.rating}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF825800)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        tint = BharatTextSecondary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = business.timings,
                        fontSize = 11.sp,
                        color = BharatTextSecondary
                    )
                }

                Button(
                    onClick = onCall,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Call Store", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BookingItemCard(
    booking: ServiceBooking,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Booking #${booking.id}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = BharatNavy
                    )
                    Text(
                        text = "${booking.trade} - ${booking.workerName}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BharatTextPrimary
                    )
                }

                Surface(
                    color = when (booking.status) {
                        BookingStatus.CONFIRMED -> BharatEmeraldContainer
                        BookingStatus.IN_PROGRESS -> BharatSaffronContainer
                        BookingStatus.COMPLETED -> Color(0xFFE8F5E9)
                        else -> Color(0xFFFFF3E0)
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = booking.status.label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (booking.status) {
                            BookingStatus.CONFIRMED -> BharatEmerald
                            BookingStatus.IN_PROGRESS -> BharatSaffron
                            BookingStatus.COMPLETED -> BharatEmerald
                            else -> BharatSaffron
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Slot: ${booking.date} (${booking.timeSlot})",
                fontSize = 12.sp,
                color = BharatTextSecondary
            )
            Text(
                text = "Address: ${booking.address}",
                fontSize = 12.sp,
                color = BharatTextSecondary
            )
            Text(
                text = "Problem: ${booking.description}",
                fontSize = 12.sp,
                color = BharatTextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = BharatBorder, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Paid via: ${booking.paymentMethod}",
                    fontSize = 12.sp,
                    color = BharatTextSecondary
                )
                Text(
                    text = "Total: ₹${booking.totalAmount} (incl. ₹${booking.platformFee} platform fee)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BharatNavy
                )
            }
        }
    }
}

@Composable
fun EmptyListPlaceholder(
    message: String,
    onAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.SearchOff,
            contentDescription = null,
            tint = BharatTextSecondary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = message,
            color = BharatTextSecondary,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onAction,
            colors = ButtonDefaults.buttonColors(containerColor = BharatNavy)
        ) {
            Text("Reset Search")
        }
    }
}

@Composable
fun ServiceBookingDialog(
    worker: SkillWorker,
    viewModel: BharatViewModel,
    onDismiss: () -> Unit,
    onConfirmed: (ServiceBooking) -> Unit
) {
    val context = LocalContext.current
    var date by remember { mutableStateOf("Tomorrow") }
    var timeSlot by remember { mutableStateOf("11:00 AM - 01:00 PM") }
    var address by remember { mutableStateOf("Godaulia, Varanasi") }
    var description by remember { mutableStateOf("") }
    var payViaWallet by remember { mutableStateOf(true) }

    val walletBalance by viewModel.walletBalance.collectAsState()
    val platformPercent = viewModel.platformEarnings.collectAsState().value.defaultCommissionPercent

    val serviceCharge = worker.hourlyOrVisitRate
    val platformFee = (serviceCharge * platformPercent) / 100
    val totalAmount = serviceCharge + platformFee

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Book ${worker.name}", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(
                    "${worker.trade.displayName} • ₹$serviceCharge visit charge",
                    fontSize = 12.sp,
                    color = BharatSaffron
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Service Date") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = timeSlot,
                    onValueChange = { timeSlot = it },
                    label = { Text("Preferred Time Slot") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Visit Address & Landmark") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Problem Description (समस्या का विवरण)") },
                    placeholder = { Text("e.g. Inverter tripping, pipe broken") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Transparent Bill Breakdown
                Surface(
                    color = BharatSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Worker Service Fee:", fontSize = 12.sp, color = BharatTextSecondary)
                            Text("₹$serviceCharge", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Platform & Safety Fee ($platformPercent%):", fontSize = 12.sp, color = BharatTextSecondary)
                            Text("₹$platformFee", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Payable:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = BharatNavy)
                            Text("₹$totalAmount", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BharatNavy)
                        }
                    }
                }

                // Payment Method Selector
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = payViaWallet,
                        onClick = { payViaWallet = true }
                    )
                    Text("Bharat Wallet (Bal: ₹$walletBalance)", fontSize = 12.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = !payViaWallet,
                        onClick = { payViaWallet = false }
                    )
                    Text("Cash on Visit (नकद भुगतान)", fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.bookService(
                        worker = worker,
                        date = date,
                        timeSlot = timeSlot,
                        address = address,
                        description = description,
                        payViaWallet = payViaWallet,
                        onSuccess = { booking ->
                            onConfirmed(booking)
                        },
                        onError = { errMsg ->
                            Toast.makeText(context, errMsg, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = BharatSaffron),
                modifier = Modifier.testTag("confirm_service_booking_button")
            ) {
                Text("Confirm Booking (₹$totalAmount)")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun RegisterWorkerDialog(
    viewModel: BharatViewModel,
    onDismiss: () -> Unit,
    onRegistered: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("5") }
    var visitRate by remember { mutableStateOf("250") }
    var area by remember { mutableStateOf("Varanasi") }
    var trade by remember { mutableStateOf(WorkerTrade.ELECTRICIAN) }
    var skillTags by remember { mutableStateOf("Wiring, MCB Repair") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Register Your Local Skill / Trade", fontWeight = FontWeight.Bold, fontSize = 17.sp)
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Join BharatSeva network and get verified customer bookings in your area.",
                    fontSize = 11.sp,
                    color = BharatTextSecondary
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name (कारीगर का नाम)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Mobile Number (फोन नंबर)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = visitRate,
                    onValueChange = { visitRate = it },
                    label = { Text("Standard Visit Rate (₹)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text("Village / Mohalla / City") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = skillTags,
                    onValueChange = { skillTags = it },
                    label = { Text("Special Skills (e.g. Fitting, Repair)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        viewModel.repository.registerNewWorker(
                            name = name,
                            trade = trade,
                            phone = phone,
                            experience = experience.toIntOrNull() ?: 3,
                            rate = visitRate.toIntOrNull() ?: 200,
                            area = area,
                            city = "Varanasi",
                            skills = skillTags.split(",").map { it.trim() }
                        )
                        onRegistered()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = BharatNavy)
            ) {
                Text("Register Now")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
