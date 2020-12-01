package other.mvvmSetup

import com.android.tools.idea.wizard.template.*

val mvvmSetupTemplate: Template
  get() = template {
    revision = 2
    name = "Fragment MVVM"
    description = "Creates a new activity along layout file."
    minApi = 16
    minBuildApi = 16
    category = Category.Fragment
    formFactor = FormFactor.Mobile
    screens = listOf(WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry)

    val fragmentName = stringParameter {
      name = "Fragment name"
      default = "Blank Fragment"
      help = "The name of the fragment class to create"
      constraints = listOf(Constraint.CLASS, Constraint.NONEMPTY)
    }

    val layoutName = stringParameter {
      name = "Layout name"
      default = "fragment_layout"
      help = "The name of the layout to create for the fragment"
      constraints = listOf(Constraint.LAYOUT, Constraint.UNIQUE, Constraint.NONEMPTY)
      suggest = { "fragment_" + classToResource(fragmentName.value) }
    }

    val viewModelName = stringParameter {
      name = "ViewModel name"
      default = "FragmentVM"
      help = "The name of the ViewModel class to create. Can be empty."
      constraints = listOf(Constraint.CLASS, Constraint.UNIQUE)
      suggest = { underscoreToCamelCase(classToResource(fragmentName.value)) + "VM" }
    }

    val factoryVMName = stringParameter {
      name = "ViewModelFactory name"
      default = "FragmentVMFactory"
      help = "The name of the ViewModelFactory class to create. Can be empty."
      constraints = listOf(Constraint.CLASS, Constraint.UNIQUE)
      suggest = { underscoreToCamelCase(classToResource(fragmentName.value)) + "VMFactory" }
    }

    val packageNameParam = stringParameter {
      name = "Package name"
      visible = { !isNewModule }
      default = "uiview"
      //constraints = listOf(Constraint.PACKAGE)
      //suggest = { extractLetters((classToResource(fragmentName.value))) }
    }

    /*val createAdapter = booleanParameter {

    }*/

    widgets(
        TextFieldWidget(fragmentName),
        TextFieldWidget(layoutName),
        TextFieldWidget(viewModelName),
        TextFieldWidget(factoryVMName),
        //CheckBoxWidget(createAdapter)
        PackageNameWidget(packageNameParam)
    )

    recipe = { data: TemplateData ->
      mvvmSetup(
          data as ModuleTemplateData,
          fragmentName.value,
          layoutName.value,
          viewModelName.value,
          factoryVMName.value,
          packageNameParam.value,
      )
    }
  }
