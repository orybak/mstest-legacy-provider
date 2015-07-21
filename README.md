### Legacy properties provider for MSTest
Since TeamCity 9.1 mstest installation paths are provided as configuration parameters.
This plugin will add them as system properties to reported agent parameters.

| Configuration Parameter  | System Parameter   |
|---|---|
| `teamcity.dotnet.mstest.*`  | `system.MSTest.*`  |

#### Building

Project is maven based.
After `mvn package` execution, plugin will be in `target` folder

