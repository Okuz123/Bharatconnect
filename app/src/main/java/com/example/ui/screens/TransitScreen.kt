package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TransitBooking
import com.example.model.TransitType
import com.example.ui.BharatViewModel
import com.example.ui.theme.*

@Composable
fun TransitScreen(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var transitTab by remember { mutableIntStateOf(0) } // 0: Local Auto/Cab, 1: Bus Booking, 2: Active Tickets
    val transitBookings by viewModel.transitBookings.collectAsState()
    val walletBalance by viewModel.walletBalance.collectAsState()

    // Cab / Auto states
    var pickup by remember { mutableStateOf("Godaulia Chowk") }
    var drop by remember { mutableStateOf("Varanasi Cantt Railway Station") }
    var selectedVehicle by remember { mutableStateOf(TransitType.AUTO_RICKSHAW) }

    val vehicleOptions = listOf(
        Triple(TransitType.AUTO_RICKSHAW, "Auto Rickshaw (ऑटो)", 110),
        Triple(TransitType.BIKE_TAXI, "Bike Taxi (बाइक)", 65),
        Triple(TransitType.CAB_MINI, "Mini Cab AC (मिनी कैब)", 210),
        Triple(TransitType.CAB_SEDAN, "Sedan AC (बड़ी गाड़ी)", 320)
    )

    // Bus Booking states
    var busFrom by remember { mutableStateOf("Varanasi (Cantt Depot)") }
    var busTo by remember { mutableStateOf("Ayodhya Dham") }
    var selectedSeat by remember { mutableStateOf("Seat 14 (Window)") }

    val busSchedules = listOf(
        BusRouteInfo("UPSRTC Janrath AC", "06:30 AM", "04 hrs", 340, "UP 65 BT 9021"),
        BusRouteInfo("UPSRTC Sleeper Express", "09:00 AM", "04.5 hrs", 420, "UP 70 ET 4410"),
        BusRouteInfo("Kashi Chariot Volvo AC", "02:15 PM", "03.8 hrs", 580, "UP 32 CZ 7719"),
        BusRouteInfo("Ordinary State Roadways", "05:00 PM", "05 hrs", 210, "UP 65 AA 3109")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BharatBgLight)
    ) {
        // Tab Header
        Surface(color = BharatNavy, shadowElevation = 2.dp) {
            TabRow(
                selectedTabIndex = transitTab,
                containerColor = BharatNavy,
                contentColor = Color.White
            ) {
                Tab(
                    selected = transitTab == 0,
                    onClick = { transitTab = 0 },
                    text = { Text("Local Cab & Auto", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = transitTab == 1,
                    onClick = { transitTab = 1 },
                    text = { Text("Intercity Bus", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = transitTab == 2,
                    onClick = { transitTab = 2 },
                    text = { Text("My Trips (${transitBookings.size})", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (transitTab) {
                0 -> {
                    // Local Cab & Auto Booking
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Book Local Auto & Cab (सवारी सेवा)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = BharatNavy
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                // Pickup Input
                                OutlinedTextField(
                                    value = pickup,
                                    onValueChange = { pickup = it },
                                    label = { Text("Pickup Location (कहाँ से)") },
                                    leadingIcon = {
                                        Icon(Icons.Default.MyLocation, contentDescription = null, tint = BharatEmerald)
                                    },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Quick Pickup chips
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    val quickPickups = listOf("Godaulia Chowk", "Assi Ghat", "BHU Main Gate", "Sigra Mall")
                                    items(quickPickups) { loc ->
                                        SuggestionChip(
                                            onClick = { pickup = loc },
                                            label = { Text(loc, fontSize = 11.sp) }
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Drop Input
                                OutlinedTextField(
                                    value = drop,
                                    onValueChange = { drop = it },
                                    label = { Text("Destination (कहाँ जाना है)") },
                                    leadingIcon = {
                                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = BharatSaffron)
                                    },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Quick Drop chips
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    val quickDrops = listOf("Cantt Railway Station", "Babatpur Airport", "Sarnath Stupa", "Kashi Vishwanath")
                                    items(quickDrops) { loc ->
                                        SuggestionChip(
                                            onClick = { drop = loc },
                                            label = { Text(loc, fontSize = 11.sp) }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Vehicle Selection
                    item {
                        Text(
                            text = "Select Vehicle Type",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = BharatNavy
                        )
                    }

                    items(vehicleOptions) { (type, name, fare) ->
                        val isSelected = selectedVehicle == type
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFF0F7FF) else MaterialTheme.colorScheme.surface
                            ),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, BharatNavy) else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedVehicle = type }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedVehicle = type }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = BharatTextPrimary
                                        )
                                        Text(
                                            text = when (type) {
                                                TransitType.AUTO_RICKSHAW -> "Fast & cheap in traffic • 3 seats"
                                                TransitType.BIKE_TAXI -> "Solo passenger with helmet • Quickest"
                                                TransitType.CAB_MINI -> "AC hatchback car • 4 seats"
                                                else -> "Spacious Sedan / SUV • 4-6 seats"
                                            },
                                            fontSize = 11.sp,
                                            color = BharatTextSecondary
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "₹$fare",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 18.sp,
                                        color = BharatNavy
                                    )
                                    Text(
                                        text = "Fixed Fare",
                                        fontSize = 10.sp,
                                        color = BharatEmerald,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }

                    // Book Ride Action Button
                    item {
                        val currentFare = vehicleOptions.firstOrNull { it.first == selectedVehicle }?.third ?: 110
                        Button(
                            onClick = {
                                viewModel.bookTransit(
                                    type = selectedVehicle,
                                    fromLoc = pickup,
                                    toLoc = drop,
                                    fare = currentFare,
                                    operatorName = when (selectedVehicle) {
                                        TransitType.AUTO_RICKSHAW -> "Raju Auto Driver"
                                        TransitType.BIKE_TAXI -> "Vikram Moto"
                                        TransitType.CAB_MINI -> "Sanjay Cab"
                                        else -> "Amit Express Sedan"
                                    },
                                    vehicleNo = "UP 65 ${(1000..9999).random()}",
                                    payViaWallet = true,
                                    onSuccess = { ride ->
                                        transitTab = 2 // Switch to My Trips
                                        Toast.makeText(context, "Ride Confirmed! OTP is ${ride.otp}", Toast.LENGTH_LONG).show()
                                    },
                                    onError = { err ->
                                        Toast.makeText(context, err, Toast.LENGTH_LONG).show()
                                    }
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BharatSaffron),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("book_cab_button")
                        ) {
                            Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Book Now for ₹$currentFare (Wallet Bal: ₹$walletBalance)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                1 -> {
                    // Intercity Bus Booking Tab
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "State Roadways & Private Buses (बस टिकट बुकिंग)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = BharatNavy
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = busFrom,
                                    onValueChange = { busFrom = it },
                                    label = { Text("From (शुरुआती शहर)") },
                                    leadingIcon = { Icon(Icons.Default.DirectionsBus, contentDescription = null) },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = busTo,
                                    onValueChange = { busTo = it },
                                    label = { Text("To Destination (गंतव्य शहर)") },
                                    leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = selectedSeat,
                                    onValueChange = { selectedSeat = it },
                                    label = { Text("Seat Preference") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Available Buses on this Route",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = BharatNavy
                        )
                    }

                    items(busSchedules) { bus ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            text = bus.busName,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = BharatNavy
                                        )
                                        Text(
                                            text = "Dep: ${bus.departureTime} • Duration: ${bus.duration}",
                                            fontSize = 12.sp,
                                            color = BharatTextSecondary
                                        )
                                        Text(
                                            text = "Bus Reg: ${bus.busNumber}",
                                            fontSize = 11.sp,
                                            color = BharatTextSecondary
                                        )
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "₹${bus.fare}",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 17.sp,
                                            color = BharatNavy
                                        )
                                        Text(
                                            text = "Per Seat",
                                            fontSize = 10.sp,
                                            color = BharatTextSecondary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Button(
                                    onClick = {
                                        viewModel.bookTransit(
                                            type = TransitType.INTERCITY_BUS,
                                            fromLoc = busFrom,
                                            toLoc = busTo,
                                            fare = bus.fare,
                                            operatorName = bus.busName,
                                            vehicleNo = bus.busNumber,
                                            seat = selectedSeat,
                                            payViaWallet = true,
                                            onSuccess = { ride ->
                                                transitTab = 2
                                                Toast.makeText(context, "Bus Ticket Booked! PNR #${ride.otp}", Toast.LENGTH_LONG).show()
                                            },
                                            onError = { err ->
                                                Toast.makeText(context, err, Toast.LENGTH_LONG).show()
                                            }
                                        )
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.ConfirmationNumber, contentDescription = null, tint = Color.White)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Book Ticket (₹${bus.fare})", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // My Booked Trips & Tickets
                    if (transitBookings.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = BharatTextSecondary, modifier = Modifier.size(48.dp))
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("No transit trips booked yet.", color = BharatTextSecondary)
                            }
                        }
                    } else {
                        items(transitBookings) { trip ->
                            TransitTicketCard(
                                trip = trip,
                                onCall = { viewModel.dialPhone(context, trip.driverPhone) }
                            )
                        }
                    }
                }
            }
        }
    }
}

data class BusRouteInfo(
    val busName: String,
    val departureTime: String,
    val duration: String,
    val fare: Int,
    val busNumber: String
)

@Composable
fun TransitTicketCard(
    trip: TransitBooking,
    onCall: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = trip.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = BharatNavy
                )
                Surface(
                    color = BharatEmeraldContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = trip.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatEmerald,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TripOrigin, contentDescription = null, tint = BharatEmerald, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = trip.fromLocation, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = BharatSaffron, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = trip.toLocation, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = BharatBorder, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Driver/Operator: ${trip.driverOrOperator}",
                        fontSize = 12.sp,
                        color = BharatTextSecondary
                    )
                    Text(
                        text = "Plate: ${trip.vehicleNumber} • OTP: ${trip.otp}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatSaffron
                    )
                    if (trip.seatNumber != null) {
                        Text(
                            text = "Seat: ${trip.seatNumber}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BharatNavy
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "₹${trip.fare}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BharatNavy
                    )
                    OutlinedButton(
                        onClick = onCall,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Call", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
