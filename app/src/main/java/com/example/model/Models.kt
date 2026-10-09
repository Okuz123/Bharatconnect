package com.example.model

/**
 * Verification state for users and skilled workers.
 */
enum class VerificationTier(val label: String, val badgeTitle: String) {
    UNVERIFIED("Unverified", "Basic User"),
    PHONE_VERIFIED("Phone Verified", "OTP Verified"),
    AADHAAR_VERIFIED("Aadhaar Verified", "Govt ID Verified"),
    MASTER_ARTISAN("Skill Certified", "Master Artisan / PM Vishwakarma")
}

data class UserProfile(
    val id: String = "user_101",
    val name: String = "Rajesh Sharma",
    val phone: String = "+91 98765 43210",
    val location: String = "Civil Lines, Varanasi, UP",
    val state: String = "Uttar Pradesh",
    val pinCode: String = "221002",
    val verificationTier: VerificationTier = VerificationTier.AADHAAR_VERIFIED,
    val aadhaarLast4: String = "4829",
    val occupation: String = "Citizen & Local Contractor"
)

enum class WorkerTrade(val displayName: String, val hindiName: String) {
    ELECTRICIAN("Electrician", "बिजली मिस्त्री"),
    PLUMBER("Plumber", "नलसाज / प्लंबर"),
    CARPENTER("Carpenter", "बढ़ई / कारपेंटर"),
    PAINTER("Painter", "पेंटर / रंगाई"),
    MASON("Mason / Rajmistri", "राजमिस्त्री"),
    AC_TECHNICIAN("AC & Fridge Repair", "एसी / फ्रिज रिपेयर"),
    WELDER("Welder / Fabricator", "वेल्डर"),
    BLACKSMITH("Lohar / Blacksmith", "लोहार"),
    POTTER("Kumhar / Potter", "कुम्हार"),
    MOTOR_MECHANIC("Mechanic", "मोटर मैकेनिक"),
    DRIVER("Professional Driver", "ड्राइवर"),
    COOK("Cook / Halwai", "रसोइया / हलवाई"),
    PANDIT_JI("Pandit Ji / Pujari", "पंडित जी / पूजा पाठ"),
    TAILOR("Tailor / Darzi", "दर्जी")
}

data class SkillWorker(
    val id: String,
    val name: String,
    val trade: WorkerTrade,
    val phone: String,
    val experienceYears: Int,
    val rating: Double,
    val reviewsCount: Int,
    val hourlyOrVisitRate: Int,
    val villageOrArea: String,
    val city: String,
    val isAvailable: Boolean,
    val verificationTier: VerificationTier,
    val vishwakarmaBeneficiary: Boolean = false,
    val specialSkills: List<String> = emptyList(),
    val completedJobs: Int = 0
)

enum class BusinessCategory(val displayName: String) {
    KIRANA("Kirana & General Store"),
    MEDICAL("Pharmacy & Medical"),
    GARAGE("Auto & Bike Garage"),
    HARDWARE("Hardware & Sanitary"),
    DAIRY("Dairy & Sweets"),
    AGRI_SEVA("Kisan Agri Center & Seeds"),
    BOUTIQUE("Boutique & Cloth Store")
}

data class LocalBusiness(
    val id: String,
    val name: String,
    val category: BusinessCategory,
    val ownerName: String,
    val phone: String,
    val address: String,
    val rating: Double,
    val timings: String,
    val isVerified: Boolean = true,
    val deliveryAvailable: Boolean = true
)

enum class BookingStatus(val label: String) {
    PENDING("Pending Confirmation"),
    CONFIRMED("Confirmed"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

data class ServiceBooking(
    val id: String,
    val workerId: String,
    val workerName: String,
    val trade: String,
    val date: String,
    val timeSlot: String,
    val address: String,
    val description: String,
    val serviceCharge: Int,
    val platformFee: Int,
    val totalAmount: Int,
    val paymentMethod: String, // "Bharat Wallet", "Cash on Visit"
    val status: BookingStatus = BookingStatus.PENDING,
    val bookingTimestamp: Long = System.currentTimeMillis()
)

enum class TransitType {
    AUTO_RICKSHAW,
    BIKE_TAXI,
    CAB_MINI,
    CAB_SEDAN,
    INTERCITY_BUS
}

data class TransitBooking(
    val id: String,
    val type: TransitType,
    val title: String,
    val fromLocation: String,
    val toLocation: String,
    val fare: Int,
    val platformCommission: Int,
    val otp: String,
    val driverOrOperator: String,
    val vehicleNumber: String,
    val driverPhone: String,
    val status: String, // "Arriving in 4 mins", "Confirmed", "Ticket Generated"
    val seatNumber: String? = null,
    val travelDate: String = "Today",
    val departureTime: String = "10:30 AM"
)

enum class SchemeCategory(val title: String, val hindiTitle: String) {
    ALL("All Schemes", "सभी योजनाएं"),
    ARTISANS("Artisans & Skilled", "कारीगर व शिल्पकार"),
    SKILL_PROGRAMS("Skill Training", "कौशल विकास"),
    FARMERS("Farmers", "किसान"),
    WOMEN("Women", "महिलाएं"),
    SMALL_BUSINESS("Small Business", "लघु उद्योग"),
    HEALTH_HOUSING("Health & Housing", "स्वास्थ्य व आवास")
}

data class GovernmentScheme(
    val id: String,
    val title: String,
    val hindiTitle: String,
    val ministry: String,
    val category: SchemeCategory,
    val keyBenefits: List<String>,
    val financialAssistance: String,
    val eligibility: String,
    val requiredDocuments: List<String>,
    val officialPortalUrl: String,
    val isPopular: Boolean = false,
    val activeApplicationsCount: Int = 12500
)

data class FormAssistanceRequest(
    val id: String,
    val applicantName: String,
    val applicantPhone: String,
    val schemeId: String,
    val schemeTitle: String,
    val userAddress: String,
    val notes: String,
    val status: String = "Under Review", // "Under Review", "Assistance Assigned", "Submitted to Portal"
    val telegramAgent: String = "@ArjunRajput04",
    val timestamp: Long = System.currentTimeMillis()
)

data class VerificationSubmission(
    val id: String,
    val applicantName: String,
    val phone: String,
    val roleType: String, // "Skilled Worker", "Local Business", "Citizen"
    val tradeOrBusiness: String,
    val docType: String, // "Aadhaar Card", "Voter ID", "Trade Certificate"
    val docNumber: String,
    val status: String = "Pending Approval", // "Pending Approval", "Approved", "Rejected"
    val submissionDate: String = "Today"
)

enum class TransactionType {
    CREDIT,
    DEBIT
}

data class WalletTransaction(
    val id: String,
    val title: String,
    val amount: Int,
    val type: TransactionType,
    val date: String,
    val category: String, // "Recharge", "Service Booking", "Ride Booking", "Cashback", "Form Assistance"
    val balanceAfter: Int
)

data class PlatformEarningsSummary(
    val totalRevenue: Double = 34500.0,
    val totalPlatformCommission: Double = 3450.0,
    val totalGrossBookings: Int = 148,
    val totalVerifiedWorkers: Int = 42,
    val totalFormAssistanceRequests: Int = 26,
    val defaultCommissionPercent: Int = 8 // 8% platform fee
)
