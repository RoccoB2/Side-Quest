package edu.towson.cosc435.kraft.sidequest.data.model

// data class for holding data fetched from https://zenquotes.io/api/random
// does not need to be stored in a database
data class Quote (
    var q: String, // string for holding the quote fetched
    var a: String // string for holding the name of the author of the quote
)

