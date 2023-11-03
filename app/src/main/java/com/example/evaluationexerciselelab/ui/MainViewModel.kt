import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluationexerciselelab.data.api.ApiHelper
import com.example.evaluationexerciselelab.data.local.DatabaseHelper
import kotlinx.coroutines.launch
import com.example.evaluationexerciselelab.ui.base.UiState
import com.example.evaluationexerciselelab.data.model.ApiStudentsItem

class MainViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<ApiStudentsItem>>>()

    init {
        fetchStudents()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun fetchStudents() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val studentsFromApi = apiHelper.getStudents()
                uiState.postValue(UiState.Success(studentsFromApi.students))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<ApiStudentsItem>>> {
        return uiState
    }

}