package com.example.reminderpill.utils

class RefillModal {

    var mediName: String = ""
    var mediImg: Int = 0
    var stock : String = ""

    constructor(medicinceName: String, medicinceImg: Int , stock: String) {
        this.mediName = medicinceName
        this.mediImg = medicinceImg
        this.stock = stock
    }
}