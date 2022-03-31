package com.assignment.myapplication1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.assignment.myapplication1.imageRepository.ImageRepository
import com.assignment.myapplication1.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    /**
     * It initiate call to APIService class to fetch response.
     * Response status either be any one the states SUCCESS or ERROR or LOADING. Based state data
     * will be populated to View.
     */
    fun getImages() = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))
        try {
            emit(Result.success(data = repository.getImages().images))
        } catch (httpException: HttpException) {
            emit(
                Result.failure(
                    data = null,
                    message = getErrorMessage(httpException.code())
                )
            )
        } catch (exception: Exception) {
            emit(
                Result.failure(
                    data = null,
                    message = exception.message ?: "Error Occurred while fetching data!"
                )
            )
        }
    }

    private fun getErrorMessage(httpCode: Int): String {
        return when (httpCode) {
            401 -> "Accessing Unauthorised request"
            404 -> "Not Found.\n\nPlease validate the requested URL."
            500 -> "Server not found Error"
            else -> "Something went wrong!!"
        }
    }
}