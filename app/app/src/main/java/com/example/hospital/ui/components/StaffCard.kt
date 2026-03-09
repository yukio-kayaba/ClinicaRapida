package com.example.hospital.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hospital.core.network.ApiConfig
import com.example.hospital.core.network.CoilImageLoader
import com.example.hospital.data.model.Staff
import com.example.hospital.ui.theme.CardBackground
import com.example.hospital.ui.theme.TextPrimary
import com.example.hospital.ui.theme.TextSecondary
import com.example.hospital.ui.theme.TopBlue

@Composable
fun StaffCard(
    staff: Staff,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circular avatar with photo or initials
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(TopBlue.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    val fotoUrl = staff.imagenes.firstOrNull()
                    if (fotoUrl != null && fotoUrl.isNotEmpty()) {
                        val context = androidx.compose.ui.platform.LocalContext.current
                        AsyncImage(
                            model = if (fotoUrl.startsWith("http://") || fotoUrl.startsWith("https://")) {
                                fotoUrl
                            } else {
                                ApiConfig.buildImageUrl(fotoUrl)
                            },
                            imageLoader = CoilImageLoader.get(context),
                            contentDescription = null,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(
                            text = getInitials(staff.nombre),
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Right side: Column with name, specialty, and experience
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = staff.nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Text(
                        text = staff.especialidad,
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Text(
                        text = "${staff.experiencia} años de experiencia",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }
            }
            
            // Subtle divider line at the bottom
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = Color(0xFFE5E5E5),
                thickness = 0.5.dp
            )
        }
    }
}

private fun getInitials(name: String): String {
    val parts = name.trim().split(" ")
    return when {
        parts.size >= 2 -> "${parts[0][0]}${parts[1][0]}"
        parts.size == 1 -> parts[0].take(2).uppercase()
        else -> "??"
    }.uppercase()
}

