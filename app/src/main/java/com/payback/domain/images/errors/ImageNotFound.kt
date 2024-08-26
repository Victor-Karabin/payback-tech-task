package com.payback.domain.images.errors

class ImageNotFound(val imageId: Int) : Throwable("image with id $imageId not found")