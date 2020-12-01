package other.mvvmSetup

import com.android.tools.idea.wizard.template.ProjectTemplateData

fun buildFragment(packageName: String,
                  fragmentName: String,
                  layoutName: String,
                  viewModelName: String = "",
                  factoryViewModelName: String = "",
                  projectData: ProjectTemplateData): String {
  val sb = StringBuilder()
  sb.append("package $packageName")
      .append("\n\nimport android.os.Bundle")
      .append("\nimport android.view.LayoutInflater")
      .append("\nimport android.view.View")
      .append("\nimport android.view.ViewGroup")
      .append("\nimport androidx.fragment.app.viewModels")
      .append("\nimport androidx.lifecycle.Observer")
      .append("\nimport ${projectData.applicationPackage}.R")

  if (viewModelName.isNotEmpty())
    sb.append("\nimport org.kodein.di.DIAware")
        .append("\nimport org.kodein.di.android.x.di")
        .append("\nimport org.kodein.di.instance")

  sb.append("\n\nclass ${fragmentName}: BaseFragment()")

  if (viewModelName.isNotEmpty()) {
    sb.append(", DIAware {")

    if (factoryViewModelName.isNotEmpty())
      sb.append("\n\tprivate val vmFactory: ${factoryViewModelName} by instance()"
          + "\n\tprivate val viewModel: ${viewModelName} by viewModels { vmFactory }")
    else
      sb.append("\n\tprivate val viewModel: ${viewModelName} by viewModels()")

    sb.append("\n\n\toverride val di by di()")
  }
  else
    sb.append(" {")

  sb.append("\n\n\toverride fun onCreate(savedInstanceState: Bundle?) {")
      .append("\n\t\tsuper.onCreate(savedInstanceState)")
      .append("\n\t}")

      .append("\n\n\toverride fun onCreateView(inflater: LayoutInflater,")
      .append("\n\t                          container: ViewGroup?,")
      .append("\n\t                          savedInstanceState: Bundle?")
      .append("\n\t): View? = inflater.inflate(R.layout.${layoutName}, container, false)")

      .append("\n\n\toverride fun onViewCreated(view: View, savedInstanceState: Bundle?) {")
      .append("\n\t\tsuper.onViewCreated(view, savedInstanceState)")
      .append("\n\t}")

  if (viewModelName.isNotEmpty()) {
    sb.append("\n\n\toverride fun addObservers() {")
        .append("\n\t\taddObserver(viewModel.liveData, Observer<Any> {")
        .append("\n")
        .append("\n\t\t})")
        .append("\n\t}")
  }

  sb.append("\n}")

  return sb.toString()
}

fun buildFragmentLayout(packageName: String,
                        fragmentName: String) = """<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="${packageName}.${fragmentName}">

  <TextView
      android:id="@+id/textView"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:gravity="center"
      android:text="In developing"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintEnd_toEndOf="parent"
      app:layout_constraintStart_toStartOf="parent"
      app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>"""