package other.mvvmSetup

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.astend.intellijplugin.androidtemplate.mvvmfragment.listeners.MyProjectManagerListener.Companion.projectInstance
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import org.jetbrains.kotlin.idea.KotlinLanguage

fun RecipeExecutor.mvvmSetup(moduleData: ModuleTemplateData,
                             fragmentName: String,
                             layoutName: String,
                             viewModelName: String,
                             factoryViewModelName: String,
                             packageName: String) {
  val (projectData, srcOut, resOut, _) = moduleData
  val project = projectInstance ?: return

  addAllKotlinDependencies(moduleData)

  val virtualFiles = ProjectRootManager.getInstance(project).contentSourceRoots
  val virtSrc = virtualFiles.first { it.path.contains("src") }
  val virtRes = virtualFiles.first { it.path.contains("res") }
  val directorySrc = PsiManager.getInstance(project).findDirectory(virtSrc)!!
  val directoryRes = PsiManager.getInstance(project).findDirectory(virtRes)!!

  val newDirName = fragmentName.replace("Fragment", "").toLowerCase()
  val newPackage = "$packageName.$newDirName"

  buildFragment(newPackage, fragmentName, layoutName, viewModelName, factoryViewModelName, projectData)
      .save(directorySrc, newPackage, "${fragmentName}.kt")

  if (viewModelName.isNotEmpty()) {
    buildFragmentViewModel(newPackage, projectData.applicationPackage.toString(), viewModelName, factoryViewModelName)
        .save(directorySrc, newPackage, "${viewModelName}.kt")
  }

  buildFragmentLayout(packageName, fragmentName)
      .save(directoryRes, "layout", "${layoutName}.xml")

  open(srcOut.resolve("$newDirName/$fragmentName.kt"))
}

fun String.save(srcDir: PsiDirectory, subDirPath: String, fileName: String) {
  try {
    val destDir = subDirPath.split(".").toDir(srcDir)
    val psiFile = PsiFileFactory
        .getInstance(srcDir.project)
        .createFileFromText(fileName, KotlinLanguage.INSTANCE, this)
    destDir.add(psiFile)
  }
  catch (exc: Exception) {
    exc.printStackTrace()
  }
}

fun List<String>.toDir(srcDir: PsiDirectory): PsiDirectory {
  var result = srcDir
  forEach {
    result = result.findSubdirectory(it) ?: result.createSubdirectory(it)
  }
  return result
}