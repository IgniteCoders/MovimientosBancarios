package com.example.movimientosbancarios.data

import com.example.movimientosbancarios.utils.DatabaseManager

data class Movement (
    var id: Int,
    var quantity: Float,
    var date: Long
) {
    companion object {
        const val TABLE_NAME = "Movements"
        const val COLUMN_NAME_QUANTITY = "quantity"
        const val COLUMN_NAME_DATE = "date"

        val COLUMN_NAMES = arrayOf(
            DatabaseManager.COLUMN_NAME_ID,
            COLUMN_NAME_QUANTITY,
            COLUMN_NAME_DATE
        )
    }
}
