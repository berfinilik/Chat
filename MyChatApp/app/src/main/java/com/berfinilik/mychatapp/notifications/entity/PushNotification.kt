package com.berfinilik.mychatapp.notifications.entity

data class PushNotification(
    val data: NotificationData,
    val to: String
)