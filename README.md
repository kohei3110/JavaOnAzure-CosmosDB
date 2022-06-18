# Java on Azure Cosmos DB

## Architecture

<img src="images/architecture.png" />

## How to Deploy

- Deploy Azure Resources

    - Just click the below bottun

[![Deploy to Azure](https://aka.ms/deploytoazurebutton)](https://portal.azure.com/#create/Microsoft.Template/uri/https%3A%2F%2Fraw.githubusercontent.com%2Fkohei3110%2FJavaOnAzure-CosmosDB%2Fmaster%2Fazure%2Ftemplate.json)

## How to run this application on Azure

###  Set below credentials to GitHub secrets

| Key | Value |
| :--- | :--- |
| `AZURE_WEBAPP_PUBLISH_PROFILE_DEV` | A publish profile is a file that contains information and settings that is used to deploy applications and services to Azure **dev** environment. |
| `AZURE_WEBAPP_PUBLISH_PROFILE_PROD` | A publish profile is a file that contains information and settings that is used to deploy applications and services to Azure **prod** environment. |
| `COSMOSDB_ENDPOINT_DEV` | A Cosmos DB endpoint for **dev** environment. |
| `COSMOSDB_ENDPOINT_PROD` | A Cosmos DB endpoint for **prod** environment. |
| `COSMOSDB_KEY_DEV` | A primary key for Cosmos DB **dev**. |
| `COSMOSDB_KEY_PROD` | A primary key for Cosmos DB **prod**. |

Refs: 

* [Creating encrypted secrets for a repository](https://docs.github.com/en/actions/security-guides/encrypted-secrets#creating-encrypted-secrets-for-a-repository)

* [Get a publish profile from Azure App Service](https://docs.microsoft.com/en-us/visualstudio/azure/how-to-get-publish-profile-from-azure-app-service?view=vs-2022)


### Set below environment variables to App Service

| Key | Value |
| :--- | :--- |
| `COSMOSDB_ENDPOINT_DEV` | A Cosmos DB endpoint for **dev** environment. |
| `COSMOSDB_ENDPOINT_PROD` | A Cosmos DB endpoint for **prod** environment. |
| `COSMOSDB_KEY_DEV` | A primary key for Cosmos DB **dev**. |
| `COSMOSDB_KEY_PROD` | A primary key for Cosmos DB **prod**. |

Refs: [Configure an App Service app](https://docs.microsoft.com/en-us/azure/app-service/configure-common?tabs=portal)