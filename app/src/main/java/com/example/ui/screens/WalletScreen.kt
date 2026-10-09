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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TransactionType
import com.example.model.WalletTransaction
import com.example.ui.BharatViewModel
import com.example.ui.theme.*

@Composable
fun WalletScreen(
    viewModel: BharatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val walletBalance by viewModel.walletBalance.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val platformEarnings by viewModel.platformEarnings.collectAsState()

    var showAddMoneyDialog by remember { mutableStateOf(false) }

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
                    text = "Bharat Wallet (सेवा बटुआ)",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Seamless UPI & in-app payments for local services and transit",
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
            // Main Wallet Balance Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("wallet_balance_card")
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF0F3057),
                                        Color(0xFF1B497E),
                                        Color(0xFF00587A)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.AccountBalanceWallet,
                                        contentDescription = null,
                                        tint = BharatGold,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Available Balance",
                                        color = Color(0xFFE2E8F0),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Surface(
                                    color = Color(0x33FFFFFF),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        text = "Secured by UPI",
                                        color = Color(0xFF69F0AE),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "₹$walletBalance",
                                color = Color.White,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Action Buttons: Add Money & Withdraw
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = { showAddMoneyDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = BharatSaffron),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("add_money_button")
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Add Money", fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = {
                                        Toast.makeText(context, "Withdrawal to linked Bank/Aadhaar DBT initiated!", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Withdraw")
                                }
                            }
                        }
                    }
                }
            }

            // Quick Add Amounts
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val quickAmounts = listOf(100, 250, 500, 1000)
                    quickAmounts.forEach { amt ->
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp),
                            shadowElevation = 1.dp,
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            TextButton(
                                onClick = {
                                    viewModel.addMoney(amt)
                                    Toast.makeText(context, "₹$amt added to Bharat Wallet!", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Text("+₹$amt", fontWeight = FontWeight.Bold, color = BharatNavy, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // Platform Monetization & Owner Revenue Transparency Card
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFE082)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = BharatSaffron, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "How Everyone Earns (Fair Platform Model)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BharatNavy
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "• Local Skilled Artisans & Drivers keep 90% - 92% of all booking charges directly into their pockets.\n" +
                                   "• Platform Owner & Admin earns a transparent ${platformEarnings.defaultCommissionPercent}% platform service fee on every booking to cover verification, fraud prevention, server infrastructure & 24x7 customer support.\n" +
                                   "• Official form assistance fee (₹50) directly compensates customer service team.",
                            fontSize = 11.sp,
                            color = BharatTextPrimary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Transaction History Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = BharatNavy
                    )
                    Text(
                        text = "${transactions.size} entries",
                        fontSize = 12.sp,
                        color = BharatTextSecondary
                    )
                }
            }

            // Transactions List
            items(transactions) { tx ->
                TransactionCard(tx = tx)
            }
        }
    }

    if (showAddMoneyDialog) {
        var customAmount by remember { mutableStateOf("500") }
        AlertDialog(
            onDismissRequest = { showAddMoneyDialog = false },
            title = { Text("Add Money to Wallet", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Select payment mode via UPI, GPay, PhonePe, Paytm or Net Banking:", fontSize = 12.sp, color = BharatTextSecondary)
                    OutlinedTextField(
                        value = customAmount,
                        onValueChange = { customAmount = it },
                        label = { Text("Amount (₹)") },
                        leadingIcon = { Text("₹", fontWeight = FontWeight.Bold) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Surface(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Instant 100% cashback coupon applied on first recharge!",
                            color = BharatEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = customAmount.toIntOrNull() ?: 500
                        viewModel.addMoney(amt)
                        showAddMoneyDialog = false
                        Toast.makeText(context, "₹$amt successfully loaded into wallet!", Toast.LENGTH_LONG).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BharatNavy)
                ) {
                    Text("Pay & Recharge")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddMoneyDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun TransactionCard(tx: WalletTransaction) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (tx.type == TransactionType.CREDIT) BharatEmeraldContainer else Color(0xFFFFEBEE)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (tx.type == TransactionType.CREDIT) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                        contentDescription = null,
                        tint = if (tx.type == TransactionType.CREDIT) BharatEmerald else BharatError,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = tx.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = BharatTextPrimary
                    )
                    Text(
                        text = "${tx.category} • ${tx.date}",
                        fontSize = 11.sp,
                        color = BharatTextSecondary
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (tx.type == TransactionType.CREDIT) "+" else "-"}₹${tx.amount}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (tx.type == TransactionType.CREDIT) BharatEmerald else BharatError
                )
                Text(
                    text = "Bal: ₹${tx.balanceAfter}",
                    fontSize = 10.sp,
                    color = BharatTextSecondary
                )
            }
        }
    }
}
