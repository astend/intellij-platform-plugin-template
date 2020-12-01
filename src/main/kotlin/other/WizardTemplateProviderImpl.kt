package other

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import other.mvvmSetup.mvvmSetupTemplate

class WizardTemplateProviderImpl: WizardTemplateProvider() {
  override fun getTemplates(): List<Template> = listOf(mvvmSetupTemplate)
}