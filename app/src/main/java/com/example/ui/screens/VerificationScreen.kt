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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VerificationTier
import com.example.ui.BharatViewModel
import com.example.ui.theme.*

@Composable
fun VerificationScreen(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userProfile by viewModel.userProfile.collectAsState()
    val verifications by viewModel.verifications.collectAsState()

    var applicantName by remember { mutableStateOf(userProfile.name) }
    var applicantPhone by remember { mutableStateOf(userProfile.phone) }
    var roleType by remember { mutableStateOf("Skilled Artisan (कारीगर)") }
    var trade by remember { mutableStateOf("Electrician / Wireman") }
    var docType by remember { mutableStateOf("Aadhaar Card (आधार कार्ड)") }
    var docNumber by remember { mutableStateOf("XXXX-XXXX-4829") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BharatBgLight)
    ) {
        // Header
        Surface(color = BharatNavy, shadowElevation = 2.dp) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Streamlined User & Worker Verification",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "भारत सत्यापन प्रणाली - Pehchan Patra & Skill Credential",
                    color = Color(0xFFB0C4DE),
                    fontSize = 12.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Current User Badge Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(3.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(BharatEmeraldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = BharatEmerald,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = userProfile.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = BharatNavy
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = "Verified",
                                        tint = BharatEmerald,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = userProfile.occupation,
                                    fontSize = 12.sp,
                                    color = BharatTextSecondary
                                )
                                Text(
                                    text = "Aadhaar: XXXX-XXXX-${userProfile.aadhaarLast4}",
                                    fontSize = 11.sp,
                                    color = BharatEmerald,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = BharatBorder, thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        // 3 Tiers explanation
                        Text("Verification Tiers & Trust Badges:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BharatNavy)
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            TierBadgeChip("1. Phone OTP", "Basic", BharatNavyLight, Modifier.weight(1f))
                            TierBadgeChip("2. Govt ID", "Aadhaar/Voter", BharatEmerald, Modifier.weight(1f))
                            TierBadgeChip("3. Master Skill", "Vishwakarma", BharatSaffron, Modifier.weight(1f))
                        }
                    }
                }
            }

            // New Verification Application Form
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Apply for Verified Worker / Shop Badge",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = BharatNavy
                        )
                        Text(
                            text = "Submit government proof to earn the Green Trust Badge and get 3x more bookings",
                            fontSize = 11.sp,
                            color = BharatTextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = applicantName,
                            onValueChange = { applicantName = it },
                            label = { Text("Full Name as per Aadhaar") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = applicantPhone,
                            onValueChange = { applicantPhone = it },
                            label = { Text("Mobile Number (OTP linked)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = trade,
                            onValueChange = { trade = it },
                            label = { Text("Trade / Business Name") },
                            placeholder = { Text("e.g. Carpenter, Kirana Store, Mistri") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = docType,
                            onValueChange = { docType = it },
                            label = { Text("Document Type (Aadhaar / Voter ID / Vishwakarma ID)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = docNumber,
                            onValueChange = { docNumber = it },
                            label = { Text("Document / Certificate Number") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                if (applicantName.isNotBlank() && docNumber.isNotBlank()) {
                                    viewModel.submitVerification(
                                        name = applicantName,
                                        phone = applicantPhone,
                                        role = roleType,
                                        trade = trade,
                                        docType = docType,
                                        docNumber = docNumber,
                                        onSuccess = {
                                            Toast.makeText(context, "Verification Submitted! Admin will review within 24 hours.", Toast.LENGTH_LONG).show()
                                        }
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BharatEmerald),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("submit_verification_button")
                        ) {
                            Icon(Icons.Default.Security, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Submit for Verification Review", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Verification Applications Queue
            item {
                Text(
                    text = "Verification Queue Status (${verifications.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = BharatNavy
                )
            }

            items(verifications) { sub ->
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
                            Text(
                                text = sub.applicantName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BharatNavy
                            )
                            Surface(
                                color = when (sub.status) {
                                    "Approved" -> BharatEmeraldContainer
                                    "Rejected" -> Color(0xFFFFEBEE)
                                    else -> Color(0xFFFFF3E0)
                                },
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = sub.status,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (sub.status) {
                                        "Approved" -> BharatEmerald
                                        "Rejected" -> BharatError
                                        else -> BharatSaffron
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${sub.roleType}: ${sub.tradeOrBusiness}",
                            fontSize = 12.sp,
                            color = BharatTextPrimary
                        )
                        Text(
                            text = "Proof: ${sub.docType} (${sub.docNumber})",
                            fontSize = 11.sp,
                            color = BharatTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TierBadgeChip(
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
            Text(subtitle, fontSize = 9.sp, color = BharatTextSecondary)
        }
    }
}
