passtools-java
==============

Official Java SDK for the Passtools.com API


## Overview 

Please refer to the [API Doc](https://github.com/tello/passtools-api) for the raw apis.
The SDK make is easy to manage Apple's Passbook passes through the PassTools's Api.

## How to use the SDK


Let's say you wanted to create a personalized coupon for one of your customers. You would
* create a template through the PassTools UI where you could for instance create secondary fields to capture the first and last name of your customer. Let's say those fields have the keys _first_name_, _last_name_.
* You then get the template ID from the template list page. Say the ID is 5.


From the java code, you first step is to set your API key to PassTools. You API key is supplied after you contact PassTools at help@passtools.com for early access to the API program.


```java
PassTools.apiKey = "yourKey"
```

You then call _Template.get()_ to retrieve your newly created template
```java
Template template = Template.getTemplate(5L);
```

and set the 2 secondary fields for your customer "Marie Lie"

```java
JSONObject firstNameField = template.fieldsModel.get("first_name");
firstNameField.put("value", "Marie");

JSONObject lastNameField = template.fieldsModel.get("last_name");
lastNameField.put("value", "Lie");
         
```

