package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import com.example.model.GovernmentScheme
import com.example.model.SchemeCategory
import com.example.ui.BharatViewModel
import com.example.ui.components.SupportBanner
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemesScreen(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val schemes by viewModel.filteredSchemes.collectAsState()
    val selectedCategory by viewModel.selectedSchemeCategory.collectAsState()
    val searchQuery by viewModel.schemeSearchQuery.collectAsState()

    var selectedSchemeForHelp by remember { mutableStateOf<GovernmentScheme?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BharatBgLight)
    ) {
        // Top Search Header
        Surface(color = BharatNavy, shadowElevation = 2.dp) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSchemeSearch(it) },
                    placeholder = {
                        Text(
                            "Search PM Vishwakarma, PMKVY, Mudra, Awas...",
                            color = Color(0xFFA0AEC0),
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = BharatSaffron)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSchemeSearch("") }) {
                                Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
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
                        .testTag("schemes_search_input")
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Customer Support Banner - Form Filling Assistance
            item {
                SupportBanner(viewModel = viewModel)
            }

            // Category Chips
            item {
                Column {
                    Text(
                        text = "Government Schemes & Skill Programs",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = BharatNavy
                    )
                    Text(
                        text = "सभी सरकारी योजनाएं एवं कौशल विकास कार्यक्रम एक साथ",
                        fontSize = 12.sp,
                        color = BharatTextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(SchemeCategory.values()) { category ->
                            val isSelected = selectedCategory == category
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectSchemeCategory(category) },
                                label = { Text(category.title) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = BharatNavy,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Schemes List
            if (schemes.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Policy, contentDescription = null, tint = BharatTextSecondary, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No matching schemes found.", color = BharatTextSecondary)
                    }
                }
            } else {
                items(schemes) { scheme ->
                    SchemeCard(
                        scheme = scheme,
                        onNeedHelp = { selectedSchemeForHelp = scheme },
                        onVisitPortal = {
                            try {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(scheme.officialPortalUrl))
                                context.startActivity(browserIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Portal: ${scheme.officialPortalUrl}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }

    // Form Assistance Dialog
    if (selectedSchemeForHelp != null) {
        val scheme = selectedSchemeForHelp!!
        FormAssistanceDialog(
            scheme = scheme,
            viewModel = viewModel,
            onDismiss = { selectedSchemeForHelp = null },
            onSubmitSuccess = {
                selectedSchemeForHelp = null
                Toast.makeText(context, "Request Submitted! Telegram Support @ArjunRajput04 will assist you.", Toast.LENGTH_LONG).show()
            }
        )
    }
}

@Composable
fun SchemeCard(
    scheme: GovernmentScheme,
    onNeedHelp: () -> Unit,
    onVisitPortal: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("scheme_card_${scheme.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Ministry Tag
            Text(
                text = scheme.ministry.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = BharatSaffron,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Titles
            Text(
                text = scheme.title,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = BharatNavy
            )
            Text(
                text = scheme.hindiTitle,
                fontSize = 13.sp,
                color = BharatTextSecondary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Financial Assistance Banner
            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.MonetizationOn,
                        contentDescription = null,
                        tint = BharatEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = scheme.financialAssistance,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BharatEmerald
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Key Highlights Preview
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                val previewBenefits = if (expanded) scheme.keyBenefits else scheme.keyBenefits.take(3)
                previewBenefits.forEach { benefit ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("•", color = BharatSaffron, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 6.dp))
                        Text(
                            text = benefit,
                            fontSize = 12.sp,
                            color = BharatTextPrimary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Expandable full details
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HorizontalDivider(color = BharatBorder, thickness = 0.8.dp)

                    // Eligibility
                    Text("Eligibility Criteria (पात्रता):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = BharatNavy)
                    Text(scheme.eligibility, fontSize = 12.sp, color = BharatTextPrimary)

                    // Required Documents
                    Text("Required Documents (आवश्यक दस्तावेज):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = BharatNavy)
                    scheme.requiredDocuments.forEach { doc ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BharatEmerald, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(doc, fontSize = 11.sp, color = BharatTextSecondary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Toggle Expand Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) "Show Less" else "View All Benefits & Documents",
                    color = BharatNavyLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = BharatNavyLight,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = BharatBorder, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onVisitPortal,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Official Portal", fontSize = 11.sp)
                }

                Button(
                    onClick = onNeedHelp,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BharatSaffron),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("apply_scheme_${scheme.id}")
                ) {
                    Icon(Icons.Default.SupportAgent, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Help to Fill Form", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FormAssistanceDialog(
    scheme: GovernmentScheme,
    viewModel: BharatViewModel,
    onDismiss: () -> Unit,
    onSubmitSuccess: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("Rameshwar Prasad") }
    var phone by remember { mutableStateOf("+91 98765 43210") }
    var address by remember { mutableStateOf("Godaulia, Varanasi, UP") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Assistance to Fill Form", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(scheme.title, fontSize = 12.sp, color = BharatSaffron)
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Telegram agent banner
                Surface(
                    color = Color(0xFFE1F5FE),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, tint = BharatTelegramBlue, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Real-Time Support Agent: @ArjunRajput04", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0277BD))
                            Text("We will guide you through docs & portal submission.", fontSize = 10.sp, color = Color(0xFF01579B))
                        }
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Applicant Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Mobile Number (Aadhaar Linked)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Village / City & State") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Specific Question / Documents You Have") },
                    placeholder = { Text("e.g. Need help with Aadhaar OTP or Bank passbook") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.submitSchemeFormAssistance(
                        scheme = scheme,
                        name = name,
                        phone = phone,
                        address = address,
                        notes = notes,
                        onSuccess = {
                            onSubmitSuccess()
                            // Also offer immediate Telegram open
                            viewModel.openTelegramSupport(context)
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = BharatNavy),
                modifier = Modifier.testTag("submit_form_assistance_button")
            ) {
                Text("Submit & Chat with Support")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
