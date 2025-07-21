package com.paras_test_android.assignment_paras

/**
 * Data classes for expense statistics menu - they could be in separate files
 *
 */

/**
 * Data class for expense title frequency tracking
 */
data class ExpenseTitleFrequency(val title: String, val count: Int)

/**
 * Data class that tracks category frequency tracking
 */
data class ExpenseCategoryFrequency(val category: String, val count: Int)
/**
 * Data class holding average amount of expenses per category
 */
data class CategoryAverageAmount(val category: String, val averageAmount: Double)
