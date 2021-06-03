package com.pentechnologies.playfair

import java.lang.Exception

class TooShortOrTooLongKeyException: Exception() {
    override val message: String? = "Key length should between 8 and 25 characters long"
}