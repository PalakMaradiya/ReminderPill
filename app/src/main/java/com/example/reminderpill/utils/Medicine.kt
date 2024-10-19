package com.example.reminderpill.utils

class Medicine(
    var userId: String,
    var medicineName: String,
    var medicineTypeTitle: String,
    var medicineTypeImage: String,
    var medicineTime: String,
    var medicineReminderTime: String,
    var medicineReminderDate: String,
    var medicineSchedule: String,
    var medicineDuration: Int,
    var medicineFrequency: Int,
    var medicineRefillTime: String,
    var medicineInstructions: String,
    var medicineStatus: Int
) {

    constructor(
        userId: String,
        medicineName: String,
        medicineTypeTitle: String,
        medicineTypeImage: String,
        medicineTime: String
    ) : this(
        userId,
        medicineName,
        medicineTypeTitle,
        medicineTypeImage,
        medicineTime,
        "",
        "",
        "",
        0,
        0,
        "",
        "",
        0
    )
}
