package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BharatRepository {

    // Current logged-in user profile
    private val _userProfile = MutableStateFlow(
        UserProfile(
            id = "usr_001",
            name = "Rameshwar Prasad",
            phone = "+91 98765 43210",
            location = "Godaulia, Varanasi, UP",
            state = "Uttar Pradesh",
            pinCode = "221001",
            verificationTier = VerificationTier.AADHAAR_VERIFIED,
            aadhaarLast4 = "7731",
            occupation = "Citizen & Local Shopkeeper"
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // Wallet Balance & Transactions
    private val _walletBalance = MutableStateFlow(1450)
    val walletBalance: StateFlow<Int> = _walletBalance.asStateFlow()

    private val _transactions = MutableStateFlow(
        listOf(
            WalletTransaction(
                id = "tx_101",
                title = "Added to Wallet via UPI",
                amount = 1000,
                type = TransactionType.CREDIT,
                date = "Yesterday, 04:15 PM",
                category = "Recharge",
                balanceAfter = 1450
            ),
            WalletTransaction(
                id = "tx_102",
                title = "Auto Rickshaw to Cantt Station",
                amount = 120,
                type = TransactionType.DEBIT,
                date = "2 days ago",
                category = "Ride Booking",
                balanceAfter = 450
            ),
            WalletTransaction(
                id = "tx_103",
                title = "Plumber Service Payment",
                amount = 350,
                type = TransactionType.DEBIT,
                date = "4 days ago",
                category = "Service Booking",
                balanceAfter = 570
            ),
            WalletTransaction(
                id = "tx_104",
                title = "Cashback on PM Scheme Help",
                amount = 50,
                type = TransactionType.CREDIT,
                date = "5 days ago",
                category = "Cashback",
                balanceAfter = 920
            )
        )
    )
    val transactions: StateFlow<List<WalletTransaction>> = _transactions.asStateFlow()

    // Skilled Workers
    private val _workers = MutableStateFlow(
        listOf(
            SkillWorker(
                id = "w_01",
                name = "Santosh Kumar Vishwakarma",
                trade = WorkerTrade.CARPENTER,
                phone = "+91 94150 11223",
                experienceYears = 14,
                rating = 4.9,
                reviewsCount = 88,
                hourlyOrVisitRate = 250,
                villageOrArea = "Lahartara",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.MASTER_ARTISAN,
                vishwakarmaBeneficiary = true,
                specialSkills = listOf("Wooden Furniture", "Door & Window Fitting", "Modular Kitchen", "Polishing"),
                completedJobs = 142
            ),
            SkillWorker(
                id = "w_02",
                name = "Mohammad Aslam",
                trade = WorkerTrade.ELECTRICIAN,
                phone = "+91 98380 44556",
                experienceYears = 9,
                rating = 4.8,
                reviewsCount = 112,
                hourlyOrVisitRate = 200,
                villageOrArea = "Sigra",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.AADHAAR_VERIFIED,
                vishwakarmaBeneficiary = false,
                specialSkills = listOf("Wiring & MCB", "Inverter Fitting", "Fan & Light Repair", "Geyser Setup"),
                completedJobs = 196
            ),
            SkillWorker(
                id = "w_03",
                name = "Gopal Yadav (Mistri)",
                trade = WorkerTrade.MASON,
                phone = "+91 97920 88991",
                experienceYears = 18,
                rating = 4.9,
                reviewsCount = 64,
                hourlyOrVisitRate = 450,
                villageOrArea = "Shivpur",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.MASTER_ARTISAN,
                vishwakarmaBeneficiary = true,
                specialSkills = listOf("House Plaster", "Tiles & Marble", "Roof Laying", "Pucca Boundary"),
                completedJobs = 78
            ),
            SkillWorker(
                id = "w_04",
                name = "Dharmendra Plumber",
                trade = WorkerTrade.PLUMBER,
                phone = "+91 91250 33445",
                experienceYears = 7,
                rating = 4.7,
                reviewsCount = 75,
                hourlyOrVisitRate = 180,
                villageOrArea = "Assi Ghat",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.AADHAAR_VERIFIED,
                vishwakarmaBeneficiary = false,
                specialSkills = listOf("Pipe Leakage", "Submersible Motor", "Water Tank Cleaning", "Tap Replacement"),
                completedJobs = 135
            ),
            SkillWorker(
                id = "w_05",
                name = "Pappu Lohar (Blacksmith)",
                trade = WorkerTrade.BLACKSMITH,
                phone = "+91 95540 66778",
                experienceYears = 22,
                rating = 5.0,
                reviewsCount = 49,
                hourlyOrVisitRate = 300,
                villageOrArea = "Pandeypur",
                city = "Varanasi",
                isAvailable = false,
                verificationTier = VerificationTier.MASTER_ARTISAN,
                vishwakarmaBeneficiary = true,
                specialSkills = listOf("Iron Grills", "Farming Tools & Sickles", "Welding Gates", "Sharpening"),
                completedJobs = 210
            ),
            SkillWorker(
                id = "w_06",
                name = "Rajesh AC Technician",
                trade = WorkerTrade.AC_TECHNICIAN,
                phone = "+91 96160 55221",
                experienceYears = 8,
                rating = 4.8,
                reviewsCount = 92,
                hourlyOrVisitRate = 350,
                villageOrArea = "Bhelupur",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.AADHAAR_VERIFIED,
                vishwakarmaBeneficiary = false,
                specialSkills = listOf("Split & Window AC", "Gas Charging", "Fridge Compressor", "Washing Machine"),
                completedJobs = 160
            ),
            SkillWorker(
                id = "w_07",
                name = "Pt. Radheshyam Shastri",
                trade = WorkerTrade.PANDIT_JI,
                phone = "+91 94500 77112",
                experienceYears = 20,
                rating = 5.0,
                reviewsCount = 140,
                hourlyOrVisitRate = 501,
                villageOrArea = "Kashi Vishwanath Marg",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.MASTER_ARTISAN,
                vishwakarmaBeneficiary = false,
                specialSkills = listOf("Griha Pravesh", "Satyanarayan Katha", "Rudrabhishek", "Kundali Milan"),
                completedJobs = 320
            ),
            SkillWorker(
                id = "w_08",
                name = "Sunita Devi Tailor",
                trade = WorkerTrade.TAILOR,
                phone = "+91 99360 44889",
                experienceYears = 12,
                rating = 4.9,
                reviewsCount = 85,
                hourlyOrVisitRate = 200,
                villageOrArea = "Orderly Bazar",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.MASTER_ARTISAN,
                vishwakarmaBeneficiary = true,
                specialSkills = listOf("Blouse & Suits", "School Uniforms", "Curtain Stitching", "Alteration"),
                completedJobs = 290
            ),
            SkillWorker(
                id = "w_09",
                name = "Kishore Kumar Driver",
                trade = WorkerTrade.DRIVER,
                phone = "+91 91980 22334",
                experienceYears = 11,
                rating = 4.7,
                reviewsCount = 63,
                hourlyOrVisitRate = 400,
                villageOrArea = "Cantt Area",
                city = "Varanasi",
                isAvailable = true,
                verificationTier = VerificationTier.AADHAAR_VERIFIED,
                vishwakarmaBeneficiary = false,
                specialSkills = listOf("Manual & Auto Cars", "Highway Driving", "Commercial License", "Tour Guide"),
                completedJobs = 115
            )
        )
    )
    val workers: StateFlow<List<SkillWorker>> = _workers.asStateFlow()

    // Local Businesses
    private val _businesses = MutableStateFlow(
        listOf(
            LocalBusiness(
                id = "b_01",
                name = "Gupta Kirana & General Store",
                category = BusinessCategory.KIRANA,
                ownerName = "Ram Lal Gupta",
                phone = "+91 94150 99881",
                address = "Shop #4, Godaulia Chowk, Varanasi",
                rating = 4.8,
                timings = "7:00 AM - 10:00 PM",
                isVerified = true,
                deliveryAvailable = true
            ),
            LocalBusiness(
                id = "b_02",
                name = "Jan Aushadhi & City Medical Store",
                category = BusinessCategory.MEDICAL,
                ownerName = "Dr. S. K. Maurya",
                phone = "+91 98390 12345",
                address = "Near BHU Gate, Lanka, Varanasi",
                rating = 4.9,
                timings = "Open 24x7",
                isVerified = true,
                deliveryAvailable = true
            ),
            LocalBusiness(
                id = "b_03",
                name = "Kashi Kisan Agri Center & Seeds",
                category = BusinessCategory.AGRI_SEVA,
                ownerName = "Surendra Singh",
                phone = "+91 97210 65432",
                address = "Mandi Samiti Road, Shivpur, Varanasi",
                rating = 4.7,
                timings = "8:00 AM - 8:00 PM",
                isVerified = true,
                deliveryAvailable = false
            ),
            LocalBusiness(
                id = "b_04",
                name = "National Hardware & Sanitary Works",
                category = BusinessCategory.HARDWARE,
                ownerName = "Manoj Jaiswal",
                phone = "+91 93350 44112",
                address = "Rath Yatra Crossing, Varanasi",
                rating = 4.6,
                timings = "9:00 AM - 9:00 PM",
                isVerified = true,
                deliveryAvailable = true
            ),
            LocalBusiness(
                id = "b_05",
                name = "Sharma Dairy & Pure Sweets",
                category = BusinessCategory.DAIRY,
                ownerName = "Om Prakash Sharma",
                phone = "+91 94520 88771",
                address = "Thatheri Bazar, Chowk, Varanasi",
                rating = 4.9,
                timings = "6:00 AM - 10:30 PM",
                isVerified = true,
                deliveryAvailable = true
            ),
            LocalBusiness(
                id = "b_06",
                name = "Om Sai Bike & Auto Garage",
                category = BusinessCategory.GARAGE,
                ownerName = "Anil Mistri",
                phone = "+91 91290 66332",
                address = "DLW Road, Manduadih, Varanasi",
                rating = 4.8,
                timings = "8:30 AM - 8:30 PM",
                isVerified = true,
                deliveryAvailable = true
            )
        )
    )
    val businesses: StateFlow<List<LocalBusiness>> = _businesses.asStateFlow()

    // Service Bookings
    private val _bookings = MutableStateFlow(
        listOf(
            ServiceBooking(
                id = "BK_901",
                workerId = "w_02",
                workerName = "Mohammad Aslam",
                trade = "Electrician",
                date = "Tomorrow",
                timeSlot = "11:00 AM - 01:00 PM",
                address = "House 14/B, Godaulia, Varanasi",
                description = "Inverter wiring tripping issue in main hall",
                serviceCharge = 200,
                platformFee = 16,
                totalAmount = 216,
                paymentMethod = "Bharat Wallet",
                status = BookingStatus.CONFIRMED
            ),
            ServiceBooking(
                id = "BK_902",
                workerId = "w_01",
                workerName = "Santosh Kumar Vishwakarma",
                trade = "Carpenter",
                date = "12 Oct 2026",
                timeSlot = "02:00 PM - 04:00 PM",
                address = "Shop #4, Godaulia Chowk",
                description = "Wooden shelf making and lock installation",
                serviceCharge = 350,
                platformFee = 28,
                totalAmount = 378,
                paymentMethod = "Cash on Visit",
                status = BookingStatus.PENDING
            )
        )
    )
    val bookings: StateFlow<List<ServiceBooking>> = _bookings.asStateFlow()

    // Transit Bookings (Cabs, Autos, Buses)
    private val _transitBookings = MutableStateFlow(
        listOf(
            TransitBooking(
                id = "TR_881",
                type = TransitType.AUTO_RICKSHAW,
                title = "Local Auto - Cantt Station",
                fromLocation = "Godaulia Chowk",
                toLocation = "Varanasi Junction (Cantt)",
                fare = 110,
                platformCommission = 11,
                otp = "4819",
                driverOrOperator = "Dinesh Auto (UP 65 BT 4192)",
                vehicleNumber = "UP 65 BT 4192",
                driverPhone = "+91 94158 87766",
                status = "Arriving in 3 mins",
                departureTime = "10:15 AM"
            ),
            TransitBooking(
                id = "TR_882",
                type = TransitType.INTERCITY_BUS,
                title = "UPSRTC Janrath AC Bus",
                fromLocation = "Varanasi (Cantt Depot)",
                toLocation = "Prayagraj (Civil Lines)",
                fare = 260,
                platformCommission = 26,
                otp = "9201",
                driverOrOperator = "UPSRTC Express",
                vehicleNumber = "UP 70 AT 8821",
                driverPhone = "Helpline: 1800-180-2877",
                status = "Ticket Confirmed",
                seatNumber = "Seat 18 (Window)",
                travelDate = "15 Oct 2026",
                departureTime = "07:30 AM"
            )
        )
    )
    val transitBookings: StateFlow<List<TransitBooking>> = _transitBookings.asStateFlow()

    // Government Schemes & Skill India Programs
    private val _schemes = MutableStateFlow(
        listOf(
            GovernmentScheme(
                id = "SCH_01",
                title = "PM Vishwakarma Scheme",
                hindiTitle = "प्रधानमंत्री विश्वकर्मा योजना",
                ministry = "Ministry of MSME & Ministry of Skill Development",
                category = SchemeCategory.ARTISANS,
                keyBenefits = listOf(
                    "₹15,000 Free Toolkit e-Voucher",
                    "5 to 7 days Basic Skill Training with ₹500/day daily stipend",
                    "1st Tranche collateral-free loan up to ₹1,00,000 @ 5% interest",
                    "2nd Tranche loan up to ₹2,00,000 for business expansion",
                    "Official PM Vishwakarma Certificate & Identity Card",
                    "₹1 Digital transaction incentive (up to 100 tx/month)"
                ),
                financialAssistance = "₹15,000 Toolkit + ₹3 Lakh Loan @ 5%",
                eligibility = "Artisans & craftspeople working in 18 traditional trades (Carpenter, Blacksmith, Potter, Mason, Barber, Tailor, Cobbler, etc.). Min age 18 years, no prior PMEGP/Mudra active default.",
                requiredDocuments = listOf(
                    "Aadhaar Card with linked Mobile Number",
                    "Bank Account Passbook (active account)",
                    "Ration Card or Family details",
                    "Trade verification by Gram Panchayat / Urban Local Body"
                ),
                officialPortalUrl = "https://pmvishwakarma.gov.in",
                isPopular = true,
                activeApplicationsCount = 48500
            ),
            GovernmentScheme(
                id = "SCH_02",
                title = "PM Kaushal Vikas Yojana 4.0 (PMKVY)",
                hindiTitle = "प्रधानमंत्री कौशल विकास योजना ४.०",
                ministry = "Ministry of Skill Development & Entrepreneurship (MSDE)",
                category = SchemeCategory.SKILL_PROGRAMS,
                keyBenefits = listOf(
                    "100% Free Government Certified Skill Courses",
                    "New-age Industry 4.0 skills: AI, Robotics, Drone tech, Solar energy",
                    "NSDC Recognized National Skill Certificate",
                    "Direct Job Mela and placement facilitation support",
                    "Accidental insurance during the training period"
                ),
                financialAssistance = "Free Training + ₹500 Conveyance + Govt Certificate",
                eligibility = "Indian citizen aged 15-45 years, school/college dropouts or unemployed youth seeking verified vocational career skills.",
                requiredDocuments = listOf(
                    "Aadhaar Card",
                    "Educational Marksheet (8th/10th/12th if available)",
                    "Bank Account Details",
                    "Passport size photograph"
                ),
                officialPortalUrl = "https://pmkvyofficial.org",
                isPopular = true,
                activeApplicationsCount = 89200
            ),
            GovernmentScheme(
                id = "SCH_03",
                title = "PM SVANidhi (Street Vendor's AtmaNirbhar)",
                hindiTitle = "पीएम स्वनिधि योजना (रेहड़ी-पटरी वालों के लिए)",
                ministry = "Ministry of Housing and Urban Affairs (MoHUA)",
                category = SchemeCategory.SMALL_BUSINESS,
                keyBenefits = listOf(
                    "Initial working capital loan up to ₹10,000 without collateral",
                    "On timely repayment, 2nd loan up to ₹20,000 & 3rd up to ₹50,000",
                    "7% Interest subsidy credited directly to bank account",
                    "Cashback up to ₹1,200/year on digital transactions via UPI QR"
                ),
                financialAssistance = "Up to ₹50,000 Micro-loan + 7% Interest Subsidy",
                eligibility = "Street vendors, vegetable sellers, tea stalls, hawkers who hold Certificate of Vending or Urban Local Body recommendation letter.",
                requiredDocuments = listOf(
                    "Aadhaar Card",
                    "Voter ID / Driving License",
                    "Vending Identity Card or Letter of Recommendation (LoR)",
                    "Bank Account linked with Aadhaar"
                ),
                officialPortalUrl = "https://pmsvanidhi.mohua.gov.in",
                isPopular = true,
                activeApplicationsCount = 34100
            ),
            GovernmentScheme(
                id = "SCH_04",
                title = "Pradhan Mantri Awas Yojana (PMAY-G / U)",
                hindiTitle = "प्रधानमंत्री आवास योजना (पक्का मकान)",
                ministry = "Ministry of Rural Development & MoHUA",
                category = SchemeCategory.HEALTH_HOUSING,
                keyBenefits = listOf(
                    "Direct financial grant of ₹1,20,000 in plains & ₹1,30,000 in hilly areas",
                    "Additional ₹12,000 grant for toilet under Swachh Bharat Mission",
                    "90-95 person-days unskilled labour under MGNREGS (approx. ₹20,000+)",
                    "Piped drinking water, electricity (Saubhagya) & LPG connection (Ujjwala)"
                ),
                financialAssistance = "₹1.20 Lakh to ₹2.50 Lakh Direct Grant",
                eligibility = "Families without a pucca house in their name anywhere in India, living in kutcha or dilapidated houses, SECC list priority.",
                requiredDocuments = listOf(
                    "Aadhaar Number of all family members",
                    "Bank Account Details",
                    "Land / House site possession proof or Patta",
                    "MGNREGA Job Card Number (for rural)"
                ),
                officialPortalUrl = "https://pmayg.nic.in",
                isPopular = true,
                activeApplicationsCount = 112000
            ),
            GovernmentScheme(
                id = "SCH_05",
                title = "Ayushman Bharat (PM-JAY Golden Card)",
                hindiTitle = "आयुष्मान भारत - ५ लाख मुफ्त इलाज",
                ministry = "National Health Authority (NHA)",
                category = SchemeCategory.HEALTH_HOUSING,
                keyBenefits = listOf(
                    "Cashless health insurance cover up to ₹5,00,000 per family per year",
                    "Valid in all empaneled Government and leading Private hospitals nationwide",
                    "Covers 1,949 medical treatments, surgeries, ICU, medicines, diagnostics",
                    "No family size limit and no age cap; pre-existing diseases covered from day 1"
                ),
                financialAssistance = "₹5 Lakh Free Cashless Hospitalization per year",
                eligibility = "Families listed in SECC 2011 database, rural deprivation categories D1-D7, and identified urban occupational worker categories.",
                requiredDocuments = listOf(
                    "Aadhaar Card",
                    "Ration Card or Family ID",
                    "Mobile Number for OTP verification"
                ),
                officialPortalUrl = "https://beneficiary.nha.gov.in",
                isPopular = true,
                activeApplicationsCount = 145000
            ),
            GovernmentScheme(
                id = "SCH_06",
                title = "PM Kisan Samman Nidhi",
                hindiTitle = "प्रधानमंत्री किसान सम्मान निधि",
                ministry = "Ministry of Agriculture & Farmers Welfare",
                category = SchemeCategory.FARMERS,
                keyBenefits = listOf(
                    "₹6,000 yearly direct income support in three equal installments of ₹2,000",
                    "Direct transfer to Aadhaar-linked DBT bank account",
                    "Easy e-KYC directly through mobile OTP or facial recognition"
                ),
                financialAssistance = "₹6,000 / year Guaranteed DBT",
                eligibility = "Small and marginal landholding farmer families with cultivable landholding in their name.",
                requiredDocuments = listOf(
                    "Aadhaar Card",
                    "Land ownership documents (Khatoni / Khasra)",
                    "Aadhaar linked active bank passbook"
                ),
                officialPortalUrl = "https://pmkisan.gov.in",
                isPopular = false,
                activeApplicationsCount = 76000
            ),
            GovernmentScheme(
                id = "SCH_07",
                title = "Pradhan Mantri MUDRA Yojana (PMMY)",
                hindiTitle = "प्रधानमंत्री मुद्रा योजना (लघु उद्योग लोन)",
                ministry = "Department of Financial Services, Ministry of Finance",
                category = SchemeCategory.SMALL_BUSINESS,
                keyBenefits = listOf(
                    "Collateral-free business loans for trading, manufacturing & service units",
                    "Shishu: Loans up to ₹50,000 with very low interest",
                    "Kishore: Loans from ₹50,000 up to ₹5 Lakhs",
                    "Tarun: Loans from ₹5 Lakhs up to ₹10 Lakhs",
                    "MUDRA debit card issued for working capital withdrawals"
                ),
                financialAssistance = "Up to ₹10 Lakh Collateral-Free Loan",
                eligibility = "Any Indian citizen with a business idea or existing micro-enterprise (Kirana, Repair shop, Transport, Tailoring, Food stall).",
                requiredDocuments = listOf(
                    "Proof of Identity (Aadhaar / Voter ID / PAN)",
                    "Proof of Business Address",
                    "Quotation of machinery/items to be purchased",
                    "Bank statement of past 6 months"
                ),
                officialPortalUrl = "https://www.mudra.org.in",
                isPopular = true,
                activeApplicationsCount = 52000
            ),
            GovernmentScheme(
                id = "SCH_08",
                title = "e-Shram National Worker Card",
                hindiTitle = "ई-श्रम कार्ड (असंगठित कामगार सुरक्षा)",
                ministry = "Ministry of Labour & Employment",
                category = SchemeCategory.ARTISANS,
                keyBenefits = listOf(
                    "Universal Account Number (UAN) 12-digit permanent identity",
                    "Accidental insurance cover of ₹2 Lakh in case of death/permanent disability",
                    "Automatic eligibility for future social security benefits and disaster relief",
                    "Portability across all states for migrant workers"
                ),
                financialAssistance = "₹2 Lakh Insurance + Social Security Access",
                eligibility = "Unorganized workers aged 16-59 years not covered by EPFO or ESIC (Construction workers, Migrant labourers, Gig workers, Domestic helpers, Farm workers).",
                requiredDocuments = listOf(
                    "Aadhaar Number",
                    "Aadhaar-linked active Mobile Number",
                    "Bank Account details (IFSC & Account number)"
                ),
                officialPortalUrl = "https://eshram.gov.in",
                isPopular = false,
                activeApplicationsCount = 98000
            ),
            GovernmentScheme(
                id = "SCH_09",
                title = "Lakhpati Didi & Mahila Samman Scheme",
                hindiTitle = "लखपति दीदी योजना (महिला स्वयं सहायता समूह)",
                ministry = "Ministry of Rural Development",
                category = SchemeCategory.WOMEN,
                keyBenefits = listOf(
                    "Skill training to SHG women to achieve annual income above ₹1,00,000",
                    "Training in Drone flying (Namo Drone Didi), solar lamps, tailoring & food processing",
                    "Access to low-interest Community Investment Funds & bank credit linkages",
                    "Government procurement support and marketing through Saras fairs"
                ),
                financialAssistance = "Skill Training + Subsidized Micro-credit + Toolkits",
                eligibility = "Women who are members of Deendayal Antyodaya Yojana - NRLM Self-Help Groups (SHGs).",
                requiredDocuments = listOf(
                    "Aadhaar Card",
                    "SHG Membership certificate/passbook",
                    "Bank Account details",
                    "Passport photographs"
                ),
                officialPortalUrl = "https://nrlm.gov.in",
                isPopular = true,
                activeApplicationsCount = 38000
            )
        )
    )
    val schemes: StateFlow<List<GovernmentScheme>> = _schemes.asStateFlow()

    // Citizen Form Assistance Requests (Helped by Telegram Support @ArjunRajput04)
    private val _formRequests = MutableStateFlow(
        listOf(
            FormAssistanceRequest(
                id = "FAR_501",
                applicantName = "Rameshwar Prasad",
                applicantPhone = "+91 98765 43210",
                schemeId = "SCH_01",
                schemeTitle = "PM Vishwakarma Scheme",
                userAddress = "Godaulia, Varanasi, UP",
                notes = "Need assistance to register as Carpenter under Vishwakarma toolkit grant",
                status = "Assistance Assigned",
                telegramAgent = "@ArjunRajput04"
            ),
            FormAssistanceRequest(
                id = "FAR_502",
                applicantName = "Sunita Devi",
                applicantPhone = "+91 99360 44889",
                schemeId = "SCH_09",
                schemeTitle = "Lakhpati Didi Scheme",
                userAddress = "Orderly Bazar, Varanasi",
                notes = "Guidance needed for SHG loan and tailoring machine grant application",
                status = "Under Review",
                telegramAgent = "@ArjunRajput04"
            )
        )
    )
    val formRequests: StateFlow<List<FormAssistanceRequest>> = _formRequests.asStateFlow()

    // Verification submissions (Aadhaar / Trade proof)
    private val _verifications = MutableStateFlow(
        listOf(
            VerificationSubmission(
                id = "VER_301",
                applicantName = "Santosh Kumar Vishwakarma",
                phone = "+91 94150 11223",
                roleType = "Skilled Worker",
                tradeOrBusiness = "Carpenter / Master Artisan",
                docType = "Trade Certificate + Aadhaar",
                docNumber = "XXXX-XXXX-4190",
                status = "Approved",
                submissionDate = "Yesterday"
            ),
            VerificationSubmission(
                id = "VER_302",
                applicantName = "Om Sai Bike Garage",
                phone = "+91 91290 66332",
                roleType = "Local Business",
                tradeOrBusiness = "Auto & Bike Repair",
                docType = "Shop & Establishment Act + Aadhaar",
                docNumber = "XXXX-XXXX-8921",
                status = "Pending Approval",
                submissionDate = "Today"
            ),
            VerificationSubmission(
                id = "VER_303",
                applicantName = "Pappu Lohar",
                phone = "+91 95540 66778",
                roleType = "Skilled Worker",
                tradeOrBusiness = "Blacksmith / Lohar",
                docType = "PM Vishwakarma ID",
                docNumber = "VISHWA-2026-9912",
                status = "Pending Approval",
                submissionDate = "Today"
            )
        )
    )
    val verifications: StateFlow<List<VerificationSubmission>> = _verifications.asStateFlow()

    // Platform Earnings & Admin Revenue State
    private val _platformEarnings = MutableStateFlow(
        PlatformEarningsSummary(
            totalRevenue = 41250.0,
            totalPlatformCommission = 4125.0,
            totalGrossBookings = 168,
            totalVerifiedWorkers = 54,
            totalFormAssistanceRequests = 31,
            defaultCommissionPercent = 8
        )
    )
    val platformEarnings: StateFlow<PlatformEarningsSummary> = _platformEarnings.asStateFlow()

    // Admin Authentication State
    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    fun verifyAdminLogin(user: String, pass: String): Boolean {
        if (user.trim() == "admin" && pass.trim() == "1234Mnbv") {
            _isAdminLoggedIn.value = true
            return true
        }
        return false
    }

    fun adminLogout() {
        _isAdminLoggedIn.value = false
    }

    // Booking actions
    fun createServiceBooking(
        worker: SkillWorker,
        date: String,
        timeSlot: String,
        address: String,
        description: String,
        payViaWallet: Boolean
    ): ServiceBooking {
        val commissionRate = _platformEarnings.value.defaultCommissionPercent
        val serviceFee = worker.hourlyOrVisitRate
        val platformFee = (serviceFee * commissionRate) / 100
        val total = serviceFee + platformFee
        val paymentMethod = if (payViaWallet) "Bharat Wallet" else "Cash on Visit"

        val newBooking = ServiceBooking(
            id = "BK_${System.currentTimeMillis() % 10000}",
            workerId = worker.id,
            workerName = worker.name,
            trade = worker.trade.displayName,
            date = date,
            timeSlot = timeSlot,
            address = address,
            description = description.ifBlank { "Service visit requested" },
            serviceCharge = serviceFee,
            platformFee = platformFee,
            totalAmount = total,
            paymentMethod = paymentMethod,
            status = BookingStatus.CONFIRMED
        )

        _bookings.update { listOf(newBooking) + it }

        if (payViaWallet) {
            deductFromWallet(
                amount = total,
                title = "Booking for ${worker.name} (${worker.trade.displayName})",
                category = "Service Booking"
            )
        }

        // Add platform earnings commission
        _platformEarnings.update {
            it.copy(
                totalRevenue = it.totalRevenue + total,
                totalPlatformCommission = it.totalPlatformCommission + platformFee,
                totalGrossBookings = it.totalGrossBookings + 1
            )
        }

        return newBooking
    }

    fun updateBookingStatus(bookingId: String, newStatus: BookingStatus) {
        _bookings.update { list ->
            list.map { if (it.id == bookingId) it.copy(status = newStatus) else it }
        }
    }

    // Cab / Auto / Bus booking actions
    fun bookTransitRide(
        type: TransitType,
        fromLoc: String,
        toLoc: String,
        fare: Int,
        operatorName: String,
        vehicleNo: String,
        seat: String? = null,
        payViaWallet: Boolean = true
    ): TransitBooking {
        val commission = (fare * 10) / 100 // 10% ride commission for owner/admin
        val otp = ((1000..9999).random()).toString()

        val newRide = TransitBooking(
            id = "TR_${System.currentTimeMillis() % 10000}",
            type = type,
            title = when (type) {
                TransitType.AUTO_RICKSHAW -> "Auto Rickshaw Ride"
                TransitType.BIKE_TAXI -> "Bike Taxi Ride"
                TransitType.CAB_MINI -> "Mini Cab Ride"
                TransitType.CAB_SEDAN -> "Sedan Cab Ride"
                TransitType.INTERCITY_BUS -> "Intercity Bus Ticket"
            },
            fromLocation = fromLoc,
            toLocation = toLoc,
            fare = fare,
            platformCommission = commission,
            otp = otp,
            driverOrOperator = operatorName,
            vehicleNumber = vehicleNo,
            driverPhone = "+91 98${(10000000..99999999).random()}",
            status = if (type == TransitType.INTERCITY_BUS) "Ticket Confirmed" else "Driver Confirmed (OTP: $otp)",
            seatNumber = seat,
            departureTime = "Today, ${(8..20).random()}:30 PM"
        )

        _transitBookings.update { listOf(newRide) + it }

        if (payViaWallet) {
            deductFromWallet(
                amount = fare,
                title = "${newRide.title} ($fromLoc → $toLoc)",
                category = "Ride Booking"
            )
        }

        _platformEarnings.update {
            it.copy(
                totalRevenue = it.totalRevenue + fare,
                totalPlatformCommission = it.totalPlatformCommission + commission,
                totalGrossBookings = it.totalGrossBookings + 1
            )
        }

        return newRide
    }

    // Form Assistance Request creation (Telegram customer support @ArjunRajput04)
    fun submitFormAssistanceRequest(
        schemeId: String,
        schemeTitle: String,
        name: String,
        phone: String,
        address: String,
        notes: String
    ): FormAssistanceRequest {
        val req = FormAssistanceRequest(
            id = "FAR_${System.currentTimeMillis() % 10000}",
            applicantName = name,
            applicantPhone = phone,
            schemeId = schemeId,
            schemeTitle = schemeTitle,
            userAddress = address,
            notes = notes,
            status = "Assistance Assigned",
            telegramAgent = "@ArjunRajput04"
        )

        _formRequests.update { listOf(req) + it }

        // ₹50 form processing fee commission to platform
        _platformEarnings.update {
            it.copy(
                totalPlatformCommission = it.totalPlatformCommission + 50,
                totalFormAssistanceRequests = it.totalFormAssistanceRequests + 1
            )
        }

        return req
    }

    fun updateFormRequestStatus(requestId: String, status: String) {
        _formRequests.update { list ->
            list.map { if (it.id == requestId) it.copy(status = status) else it }
        }
    }

    // Verification application
    fun submitVerificationRequest(
        name: String,
        phone: String,
        roleType: String,
        trade: String,
        docType: String,
        docNumber: String
    ): VerificationSubmission {
        val sub = VerificationSubmission(
            id = "VER_${System.currentTimeMillis() % 10000}",
            applicantName = name,
            phone = phone,
            roleType = roleType,
            tradeOrBusiness = trade,
            docType = docType,
            docNumber = docNumber,
            status = "Pending Approval",
            submissionDate = "Today"
        )

        _verifications.update { listOf(sub) + it }
        return sub
    }

    fun updateVerificationStatus(verId: String, approved: Boolean) {
        _verifications.update { list ->
            list.map {
                if (it.id == verId) {
                    it.copy(status = if (approved) "Approved" else "Rejected")
                } else it
            }
        }
        if (approved) {
            _platformEarnings.update {
                it.copy(totalVerifiedWorkers = it.totalVerifiedWorkers + 1)
            }
        }
    }

    // Wallet actions
    fun addMoneyToWallet(amount: Int) {
        val newBalance = _walletBalance.value + amount
        _walletBalance.value = newBalance
        val tx = WalletTransaction(
            id = "tx_${System.currentTimeMillis() % 10000}",
            title = "Recharge via UPI / BharatPay",
            amount = amount,
            type = TransactionType.CREDIT,
            date = "Just now",
            category = "Recharge",
            balanceAfter = newBalance
        )
        _transactions.update { listOf(tx) + it }
    }

    private fun deductFromWallet(amount: Int, title: String, category: String) {
        val newBalance = (_walletBalance.value - amount).coerceAtLeast(0)
        _walletBalance.value = newBalance
        val tx = WalletTransaction(
            id = "tx_${System.currentTimeMillis() % 10000}",
            title = title,
            amount = amount,
            type = TransactionType.DEBIT,
            date = "Just now",
            category = category,
            balanceAfter = newBalance
        )
        _transactions.update { listOf(tx) + it }
    }

    // Register a new worker in local database
    fun registerNewWorker(
        name: String,
        trade: WorkerTrade,
        phone: String,
        experience: Int,
        rate: Int,
        area: String,
        city: String,
        skills: List<String>
    ) {
        val newWorker = SkillWorker(
            id = "w_${System.currentTimeMillis() % 10000}",
            name = name,
            trade = trade,
            phone = phone,
            experienceYears = experience,
            rating = 5.0,
            reviewsCount = 1,
            hourlyOrVisitRate = rate,
            villageOrArea = area,
            city = city,
            isAvailable = true,
            verificationTier = VerificationTier.PHONE_VERIFIED,
            vishwakarmaBeneficiary = false,
            specialSkills = skills,
            completedJobs = 0
        )
        _workers.update { listOf(newWorker) + it }
    }

    // Register a new local business
    fun registerNewBusiness(
        name: String,
        category: BusinessCategory,
        owner: String,
        phone: String,
        address: String,
        timings: String,
        delivery: Boolean
    ) {
        val newBiz = LocalBusiness(
            id = "b_${System.currentTimeMillis() % 10000}",
            name = name,
            category = category,
            ownerName = owner,
            phone = phone,
            address = address,
            rating = 5.0,
            timings = timings,
            isVerified = false,
            deliveryAvailable = delivery
        )
        _businesses.update { listOf(newBiz) + it }
    }

    // Admin updates commission
    fun updatePlatformCommissionPercent(percent: Int) {
        _platformEarnings.update { it.copy(defaultCommissionPercent = percent) }
    }
}
