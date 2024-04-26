package dev.kevinsalazar.tv.photosearch.utils

import dev.kevinsalazar.tv.domain.entities.Photo


val dummyPhotos
    get() = listOf(
        Photo(
            id = "Dwu85P9SOIk",
            description = "A man drinking a coffee.",
            username = "exampleuser",
            createdAt = "2016-05-03T11:00:28-04:00",
            urls = Photo.Urls(
                raw = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d",
                thumb = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=200&fit=max"
            ),
            tags = emptyList()
        ),
        Photo(
            id = "DwuAsdfSOIk",
            description = "A man drinking a tea.",
            username = "otheruser",
            createdAt = "2018-06-03T11:00:28-04:00",
            urls = Photo.Urls(
                raw = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d",
                thumb = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=200&fit=max"
            ),
            tags = emptyList()
        )
    )
