function getElements(tagName)
{
	return document.getElementsByTagName(tagName);
}

function elementSelector(selector)
{
	return document.querySelectorAll(selector);
}

function firstLetterCapitalize(fieldValue)
{
	return fieldValue.charAt(0).toUpperCase() + fieldValue.slice(1);
}


function saveValue(element)
{
	sessionStorage.setItem("pluginName", element.value);
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
	console.log("ma regex" + finalRegEx.toString());
	return inputValue.replace(finalRegEx, "");
}

function fieldFormat(htmlInput, inputName)
{
	let fieldsList = {
					  business_class:"/^[A-Z][a-zA-Z]*$/",
					  business_table_name:"/^[a-z]+_[a-z]*$/",
					  application_name:"/^[a-z]+$/",
					  portlet_class:"/^[a-zA-Z]*Portlet$/",
					  portlet_type_name:"/[A-Z]*_PORTLET$/",
					  jsp_base_name:"/^Portlet[A-Z][a-zA-Z]*$/",
					  feature_title:"/^[a-zA-Z]{6,80}$/",
					  feature_description:"/^[a-zA-Z]{6,255}$/",
					  feature_name:"[A-Z][a-zA-Z]*",
					  feature_right:"/^[A-Z][A-Z_]*$/",
					  application_class:"/^[A-Z][a-zA-Z]*$/"
						  };
	let inputValue = htmlInput.value;
	
	
	if( fieldsList.hasOwnProperty(inputName) && !inputValue.match( RegExp( fieldsList[inputName]) ) )
	{
		let newValue = eliminateChars(inputValue, "");
		
		switch(Object.keys(fieldsList).indexOf(inputName))
		{
			case 0:
				htmlInput.value = firstLetterCapitalize(newValue);
				break;
			case 1:
				newValue = eliminateChars(inputValue, "_");
				let pluginPrefix = sessionStorage.getItem("pluginName").toLowerCase()+"_";
				htmlInput.value = checkPrefix(pluginPrefix, newValue.toLowerCase());
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
	let htmlInputs = getElements("input");
	for (let i = 0; i < htmlInputs.length; i++)
	{

		if(htmlInputs[i].getAttribute("type") == "text" && htmlInputs[i].getAttribute("type"))
		{

			let inputName = htmlInputs[i].getAttribute("name");
			let htmlInput = htmlInputs[i];
			htmlInputs[i].addEventListener("focusout", function(){ fieldFormat(htmlInput, inputName);});
		}
			
	}
}

function getPluginName()
{
	let formFields = getElements("input");

	for(let i = 0; i < formFields.length; i++)
	{
		if (formFields[i].getAttribute("name")  == "name")
		{
			formFields[i].addEventListener("focusout", function(){saveValue(formFields[i]);});
		}
	}
	
}

document.addEventListener("DOMContentLoaded", function(){console.log("pluginName = " + elementSelector("input[name='plugin_name']")[0].value); addListeners(); getPluginName();});