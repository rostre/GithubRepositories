package ro.twodoors.githubrepolist.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import ro.twodoors.githubrepolist.R
import ro.twodoors.githubrepolist.api.RepositoryRetriever
import ro.twodoors.githubrepolist.ui.adapters.RepoListAdapter

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    repoList.layoutManager = LinearLayoutManager(this)

    if (isNetworkConnected()) {
      retrieveRepositories()
    } else {
      AlertDialog.Builder(this).setTitle("No Internet Connection")
              .setMessage("Please check your internet connection and try again")
              .setPositiveButton(android.R.string.ok) { _, _ -> }
              .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    refreshButton.setOnClickListener {
      retrieveRepositories()
    }
  }

  fun retrieveRepositories() {
    val mainActivityJob = Job()

    val errorHandler = CoroutineExceptionHandler { _, exception ->
      AlertDialog.Builder(this).setTitle("Error")
              .setMessage(exception.message)
              .setPositiveButton(android.R.string.ok) { _, _ -> }
              .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
    coroutineScope.launch(errorHandler) {
      val resultList = RepositoryRetriever().getRepositories()
      repoList.adapter = RepoListAdapter(resultList)
    }
  }

    private fun isNetworkConnected(): Boolean {
      val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val activeNetwork = connectivityManager.activeNetwork
      val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
      return networkCapabilities != null &&
              networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
  }
