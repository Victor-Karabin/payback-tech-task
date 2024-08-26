package com.payback.boundary.images.errors

class ImageNotFound(val imageId: Int) : Throwable("image with id $imageId not found")