package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BookingStatus
import com.example.model.GovernmentScheme
import com.example.model.SchemeCategory
import com.example.ui.BharatViewModel
import com.example.ui.theme.*

@Composable
fun AdminPortalScreen(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()

    var usernameInput by remember { mutableStateOf("admin") }
    var passwordInput by remember { mutableStateOf("1234Mnbv") }
    var loginError by remember { mutableStateOf(false) }

    if (!isAdminLoggedIn) {
        // Admin Login View
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(BharatBgLight)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(BharatNavy),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "Admin Login",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "BharatSeva Admin Portal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BharatNavy
            )
            Text(
                text = "Authorized Platform Administrators & Owners Only",
                fontSize = 12.sp,
                color = BharatTextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = usernameInput,
                        onValueChange = {
                            usernameInput = it
                            loginError = false
                        },
                        label = { Text("Username") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_username_input")
                    )

                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = {
                            passwordInput = it
                            loginError = false
                        },
                        label = { Text("Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_password_input")
                    )

                    if (loginError) {
                        Text(
                            text = "Invalid credentials. Use admin / 1234Mnbv",
                            color = BharatError,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Credentials hint
                    Surface(
                        color = BharatSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Official Admin Credentials:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BharatNavy)
                            Text("Username: admin", fontSize = 11.sp, color = BharatTextSecondary)
                            Text("Password: 1234Mnbv", fontSize = 11.sp, color = BharatTextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = {
                            val success = viewModel.loginAdmin(usernameInput, passwordInput)
                            if (success) {
                                Toast.makeText(context, "Welcome Admin!", Toast.LENGTH_SHORT).show()
                            } else {
                                loginError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_login_submit_button")
                    ) {
                        Text("Log In as Admin", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    } else {
        // Logged-in Admin Dashboard
        AdminDashboardView(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
fun AdminDashboardView(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val platformEarnings by viewModel.platformEarnings.collectAsState()
    val verifications by viewModel.verifications.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val formRequests by viewModel.formRequests.collectAsState()

    var showCommissionDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BharatBgLight)
    ) {
        // Admin Header
        Surface(color = BharatNavy, shadowElevation = 4.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = BharatGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Admin & Owner Console", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    }
                    Text("Live platform operations & revenue", color = Color(0xFFB0C4DE), fontSize = 11.sp)
                }

                Button(
                    onClick = { viewModel.logoutAdmin() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF)),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("Logout", color = Color.White, fontSize = 11.sp)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Metrics Overview Grid (Owner Earnings & GMV)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Owner Earnings & Platform Metrics", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = BharatNavy)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MetricCard(
                            title = "Admin/Owner Revenue",
                            value = "₹${platformEarnings.totalPlatformCommission.toInt()}",
                            subtitle = "${platformEarnings.defaultCommissionPercent}% platform fee",
                            color = BharatEmerald,
                            icon = Icons.Default.MonetizationOn,
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Gross Merchandise (GMV)",
                            value = "₹${platformEarnings.totalRevenue.toInt()}",
                            subtitle = "${platformEarnings.totalGrossBookings} total orders",
                            color = BharatNavy,
                            icon = Icons.Default.ShoppingBag,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MetricCard(
                            title = "Verified Workers",
                            value = "${platformEarnings.totalVerifiedWorkers}",
                            subtitle = "KYC verified network",
                            color = BharatSaffron,
                            icon = Icons.Default.Verified,
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Form Requests",
                            value = "${platformEarnings.totalFormAssistanceRequests}",
                            subtitle = "Govt Yojana queries",
                            color = Color(0xFF007791),
                            icon = Icons.Default.ContactSupport,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Platform Commission Setting Action
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Platform Commission Rate", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Current fee: ${platformEarnings.defaultCommissionPercent}% per service booking", fontSize = 11.sp, color = BharatTextSecondary)
                        }
                        Button(
                            onClick = { showCommissionDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Adjust Fee", fontSize = 12.sp)
                        }
                    }
                }
            }

            // Verification Approvals Section
            item {
                Text(
                    text = "Pending Worker & Business KYC Submissions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = BharatNavy
                )
            }

            items(verifications) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(item.applicantName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("${item.roleType} - ${item.tradeOrBusiness}", fontSize = 12.sp, color = BharatTextSecondary)
                            }
                            Surface(
                                color = if (item.status == "Approved") BharatEmeraldContainer else Color(0xFFFFF3E0),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = item.status,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.status == "Approved") BharatEmerald else BharatSaffron,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Proof Document: ${item.docType} (${item.docNumber})", fontSize = 11.sp, color = BharatTextSecondary)
                        Text("Contact: ${item.phone}", fontSize = 11.sp, color = BharatTextSecondary)

                        if (item.status != "Approved") {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { viewModel.approveVerification(item.id, false) }) {
                                    Text("Reject", color = BharatError, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        viewModel.approveVerification(item.id, true)
                                        Toast.makeText(context, "Approved ${item.applicantName}! Verified badge granted.", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = BharatEmerald),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Approve & Grant Badge", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Citizen Form Assistance Desk
            item {
                Text(
                    text = "Government Scheme Form Assistance Requests",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = BharatNavy
                )
            }

            items(formRequests) { req ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(req.schemeTitle, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BharatNavy)
                            Surface(color = BharatEmeraldContainer, shape = RoundedCornerShape(4.dp)) {
                                Text(req.status, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = BharatEmerald, modifier = Modifier.padding(4.dp))
                            }
                        }

                        Text("Applicant: ${req.applicantName} (${req.applicantPhone})", fontSize = 11.sp, color = BharatTextSecondary)
                        Text("Address: ${req.userAddress}", fontSize = 11.sp, color = BharatTextSecondary)
                        Text("Citizen Question: ${req.notes}", fontSize = 11.sp, color = BharatTextPrimary)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Support: ${req.telegramAgent}", color = BharatTelegramBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = { viewModel.dialPhone(context, req.applicantPhone) },
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Call Citizen", fontSize = 11.sp)
                                }

                                Button(
                                    onClick = { viewModel.openTelegramSupport(context) },
                                    colors = ButtonDefaults.buttonColors(containerColor = BharatTelegramBlue),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Open Telegram Desk", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Live Bookings Oversight
            item {
                Text(
                    text = "Live Service Bookings Oversight (${bookings.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = BharatNavy
                )
            }

            items(bookings) { bk ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Order #${bk.id} • ${bk.trade}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("₹${bk.totalAmount} (Fee: ₹${bk.platformFee})", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = BharatNavy)
                        }
                        Text("Worker: ${bk.workerName} • Slot: ${bk.date} (${bk.timeSlot})", fontSize = 11.sp, color = BharatTextSecondary)
                        Text("Address: ${bk.address}", fontSize = 11.sp, color = BharatTextSecondary)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            BookingStatus.values().forEach { st ->
                                val isSelected = bk.status == st
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.updateBookingStatus(bk.id, st) },
                                    label = { Text(st.name.take(4), fontSize = 10.sp) },
                                    modifier = Modifier.height(28.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCommissionDialog) {
        var pct by remember { mutableStateOf("10") }
        AlertDialog(
            onDismissRequest = { showCommissionDialog = false },
            title = { Text("Configure Platform Commission") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Commission percentage earned by owner/admin on every service booking and transit ride:", fontSize = 12.sp)
                    OutlinedTextField(
                        value = pct,
                        onValueChange = { pct = it },
                        label = { Text("Commission (%)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val v = pct.toIntOrNull() ?: 8
                        viewModel.updatePlatformCommission(v)
                        showCommissionDialog = false
                        Toast.makeText(context, "Commission updated to $v%!", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCommissionDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 11.sp, color = BharatTextSecondary, fontWeight = FontWeight.Medium)
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(subtitle, fontSize = 10.sp, color = BharatTextSecondary)
        }
    }
}
