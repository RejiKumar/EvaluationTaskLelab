import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluationexerciselelab.data.api.ApiHelper
import com.example.evaluationexerciselelab.data.local.DatabaseHelper
import com.example.evaluationexerciselelab.data.local.entity.Student
import kotlinx.coroutines.launch
import com.example.evaluationexerciselelab.ui.base.UiState
import com.example.evaluationexerciselelab.data.model.ApiStudentsItem

class MainViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper,
    private val context: Context
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<ApiStudentsItem>>>()

    init {
        if (isNetworkAvailable(context)) {
            fetchStudents()
        } else {
            fetchStudentsFromRoom()
        }
    }

    private fun fetchStudents() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val studentsFromApi = apiHelper.getStudents()
                uiState.postValue(UiState.Success(studentsFromApi.students))

                val studentsFromDb = dbHelper.getStudents()
                if (studentsFromDb.isEmpty()) {
                    val studentsToInsertInDB = mutableListOf<Student>()

                    for (apiStudent in studentsFromApi.students) {
                        val student = apiStudent.id?.let {
                            Student(
                                id = it.toInt(),
                                firstName = apiStudent.firstName,
                                lastName = apiStudent.lastName,
                                age = apiStudent.age,
                                gender = apiStudent.gender,
                                major = apiStudent.major,
                                gpa = apiStudent.gpa,
                            )
                        }
                        if (student != null) {
                            studentsToInsertInDB.add(student)
                        }
                    }
                    dbHelper.insertAll(studentsToInsertInDB)
                }
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    private fun fetchStudentsFromRoom() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val studentsFromDb = dbHelper.getStudents()
                val studentArray = arrayListOf<ApiStudentsItem>()
                for (studentDB in studentsFromDb) {
                    val student = ApiStudentsItem(
                        id = studentDB.id.toString(),
                        firstName = studentDB.firstName,
                        lastName = studentDB.lastName,
                        age = studentDB.age,
                        gender = studentDB.gender,
                        major = studentDB.major,
                        gpa = studentDB.gpa,
                    )
                    studentArray.add(student)
                }
                uiState.postValue(UiState.Offline(studentArray))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<ApiStudentsItem>>> {
        return uiState
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}