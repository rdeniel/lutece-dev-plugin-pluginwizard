var pluginName;

function getElements(attName)
{
	return document.getElementsByClassName(attName);
}

function elementSelector(selector)
{
	return document.querySelectorAll(selector);
}

function firstLetterCapitalize(fieldValue)
{
	return fieldValue.charAt(0).toUpperCase() + fieldValue.slice(1);
}

function checkPrefix(prefix, fieldValue)
{
	if( !fieldValue.startsWith(prefix) )
	{
		return prefix + fieldValue;
	}
	else
	{
		return fieldValue;
	}
}

function checkSuffix(suffix, fieldValue)
{
	if( !fieldValue.endsWith(suffix) )
	{
		return fieldValue + suffix;
	}
	else
	{
		return fieldValue;
	}
}

function eliminateChars(inputValue, exception)
{
	let startRegEx = "([^a-z";
	let finalRegEx = new RegExp( startRegEx + exception + "]+)", "gi" );
	return inputValue.replace(finalRegEx, "");
}

function fieldFormat(htmlInput, inputName)
{
	let fieldsList = [
					  "business_class",
					  "business_table_name",
					  "application_name",
					  "portlet_class",
					  "portlet_type_name",
					  "jsp_base_name",
					  "feature_title",
					  "feature_description",
					  "feature_name",
					  "feature_right",
					  "application_class"
						  ];
	let inputValue = htmlInput.value;
	
	
	if( fieldsList.includes(inputName) && !inputValue.match( RegExp( htmlInput.getAttribute( "data-pattern" ) ) ) )
	{
		let newValue = eliminateChars(inputValue, "");
		
		switch(fieldsList.indexOf(inputName))
		{
			case 0:
				htmlInput.value = firstLetterCapitalize(newValue);
				break;
			case 1:
				newValue = eliminateChars(inputValue, "_");
				htmlInput.value = checkPrefix(pluginName, newValue.toLowerCase());
				break;
			case 2:
				htmlInput.value = newValue.toLowerCase();
				break;
			case 3:
				htmlInput.value = firstLetterCapitalize( checkSuffix("Portlet", newValue) );
				break;
			case 4:
				newValue = eliminateChars(inputValue, "_");
				htmlInput.value = checkSuffix("_PORTLET", newValue.toUpperCase() );
				break;
			case 5:
				htmlInput.value = checkPrefix("Portlet", firstLetterCapitalize(newValue) );
				break;
			case 8:
				htmlInput.value = firstLetterCapitalize(newValue);
				break;
			case 9:
				newValue = eliminateChars(inputValue, "_");
				htmlInput.value = newValue.includes("_") ? newValue.toUpperCase() : checkSuffix("_", newValue.toUpperCase());
				break;
			case 10:
				htmlInput.value = firstLetterCapitalize(newValue);
				break;
		}

	}
}

function addListeners()
{
	let htmlInputs = getElements("autocorrect");
	for (let i = 0; i < htmlInputs.length; i++)
	{

		let inputName = htmlInputs[i].getAttribute("name");
		let htmlInput = htmlInputs[i];
		htmlInputs[i].addEventListener("focusout", function(){ fieldFormat(htmlInput, inputName);});

			
	}
}

function getPluginName()
{
	let formFields = getElements("autocorrect");

	for(let i = 0; i < formFields.length; i++)
	{
		if (formFields[i].getAttribute("name")  == "business_table_name")
		{
			pluginName = formFields[i].value;
		}
	}
	
}

document.addEventListener("DOMContentLoaded", function(){getPluginName(); addListeners();});