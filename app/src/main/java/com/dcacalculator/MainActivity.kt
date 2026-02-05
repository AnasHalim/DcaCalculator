// MainActivity.kt - الكود الكامل للتطبيق
package com.dcacalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.UUID

data class Asset(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val ticker: String,
    val quantity: Double,
    val averagePrice: Double,
    val assetType: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val yourAssets = listOf(
            Asset("Ethereum", "ETH-EUR", 0.870377, 1785.46, "CRYPTO"),
            Asset("Bitcoin", "BTC-EUR", 0.03302, 60032.15, "CRYPTO"),
            Asset("SAP SE", "SAP.DE", 11.00, 168.64, "STOCK"),
            Asset("Amundi Gold ETC", "GOLD", 20.133279, 164.84, "COMMODITY"),
            Asset("WisdomTree Copper", "COPPER", 11.207244, 43.46, "COMMODITY"),
            Asset("VanEck Uranium ETF", "URA", 5.00, 53.79, "ETF"),
            Asset("iShares Silver", "SILV", 48.561412, 71.21, "COMMODITY"),
            Asset("SPDR MSCI World ETF", "SWRD", 12.069714, 41.17, "ETF"),
            Asset("Xtrackers DAX ETF", "DAX", 11.00, 230.50, "ETF"),
            Asset("WisdomTree Multi Asset", "WTEU", 8.00, 20.23, "ETF")
        )
        
        setContent {
            PortfolioApp(assets = yourAssets)
        }
    }
}

@Composable
fun PortfolioApp(assets: List<Asset>) {
    var selectedAsset by remember { mutableStateOf<Asset?>(null) }
    var marketPrice by remember { mutableStateOf("") }
    var targetPrice by remember { mutableStateOf("") }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (selectedAsset == null) {
            // Main Screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "My Portfolio",
                    style = MaterialTheme.typography.headlineLarge
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyColumn {
                    items(assets.size) { index ->
                        AssetCard(
                            asset = assets[index],
                            onClick = { selectedAsset = assets[index] }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        } else {
            // DCA Calculator Screen
            DcaCalculatorScreen(
                asset = selectedAsset!!,
                onBack = { selectedAsset = null },
                marketPrice = marketPrice,
                targetPrice = targetPrice,
                onMarketPriceChange = { marketPrice = it },
                onTargetPriceChange = { targetPrice = it }
            )
        }
    }
}

@Composable
fun AssetCard(asset: Asset, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = asset.name,
                style = MaterialTheme.typography.titleLarge
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Quantity: ${asset.quantity}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Avg. Price: EUR ${asset.averagePrice}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Type: ${asset.assetType}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DcaCalculatorScreen(
    asset: Asset,
    onBack: () -> Unit,
    marketPrice: String,
    targetPrice: String,
    onMarketPriceChange: (String) -> Unit,
    onTargetPriceChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        Button(onClick = onBack) {
            Text("← Back to Portfolio")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "DCA Calculator: ${asset.name}",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Current Data
        Text(
            text = "Current Data:",
            style = MaterialTheme.typography.titleLarge
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text("Quantity: ${asset.quantity}")
        Text("Average Price: EUR ${asset.averagePrice}")
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Inputs
        OutlinedTextField(
            value = marketPrice,
            onValueChange = onMarketPriceChange,
            label = { Text("Current Market Price (EUR)") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = targetPrice,
            onValueChange = onTargetPriceChange,
            label = { Text("Target Average Price (EUR)") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Calculate Button
        Button(
            onClick = {
                // DCA calculation logic here
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CALCULATE DCA")
        }
    }
}
