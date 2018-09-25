# Turven

Simpel voorbeeld op Android viewModel + Databinding.
Hierbij wordt logica en view van elkaar gescheiden. 
Via databinding kan data rechtsreeks weergegeven worden in een view.
Ideaal voor het gebruik van services.

Databinding kan geactiveerd worden door de volgende de volgende code snippet in build.gradle (Module):

dataBinding {
        enabled = true
    }



Om gebruik te maken van de ViewModel, maken we gebruik van de volgende androidx libraries:

annotationProcessor "androidx.lifecycle:lifecycle-compiler:2.0.0-rc01"
implementation 'androidx.lifecycle:lifecycle-extensions:2.0.0-rc01'




*OPMERKING!*
Android ViewModel is GEEN vervanging op Bundle (onSaveInstanceState)! --> andere levesduur (life cycle)


