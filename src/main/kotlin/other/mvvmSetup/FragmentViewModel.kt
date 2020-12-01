package other.mvvmSetup

fun buildFragmentViewModel(packageName: String,
                           mvvmPackageName: String,
                           viewModelName: String = "",
                           factoryViewModelName: String = ""): String {
  val sb = StringBuilder()
  sb.append("package $packageName")
      .append("\n\nimport androidx.lifecycle.LiveData")
      .append("\nimport androidx.lifecycle.MutableLiveData")
      .append("\nimport androidx.lifecycle.ViewModel")
      .append("\nimport androidx.lifecycle.ViewModelProvider")
      .append("\nimport $mvvmPackageName.mvvm.BaseViewModel")

  sb.append("\n\nclass $viewModelName: BaseViewModel() {")

  sb.append("\n\tprivate val _liveData = MutableLiveData<Any>()")
      .append("\n\tval liveData: LiveData<Any> = _liveData")
      .append("\n\t").append("\n\t").append("\n\t")
      .append("\n}")

  if (factoryViewModelName.isNotEmpty())
    sb.append("\n\nclass $factoryViewModelName(): ViewModelProvider.NewInstanceFactory() {")
        .append("\n\t@Suppress(\"UNCHECKED_CAST\")")
        .append("\n\toverride fun <T: ViewModel?> create(modelClass: Class<T>): T {")
        .append("\n\t\treturn $viewModelName() as T")
        .append("\n\t}")
        .append("\n}")

  return sb.toString()
}
