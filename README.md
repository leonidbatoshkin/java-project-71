### Hexlet tests and linter status:
[![Actions Status](https://github.com/leonidbatoshkin/java-project-71/workflows/hexlet-check/badge.svg)](https://github.com/leonidbatoshkin/java-project-71/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/054ee6a6bead558e3b25/maintainability)](https://codeclimate.com/github/leonidbatoshkin/java-project-71/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/054ee6a6bead558e3b25/test_coverage)](https://codeclimate.com/github/leonidbatoshkin/java-project-71/test_coverage)

A **difference calculator** is a program that determines the difference between two data structures. This is a popular task for which there are many online services, for example: [http://www.jsondiff.com/](http://www.jsondiff.com/). A similar mechanism is used when outputting tests or when automatically tracking changes in configuration files.

Utility features:

- Support for different input formats: yaml and json
- Report generation in the form of plain text, stylish and json

Usage example:

```java
# format plain
./app --format plain path/to/file.yml another/path/file.json

Property 'follow' was added with value: false
Property 'baz' was updated. From 'bas' to 'bars'
Property 'group2' was removed

# format stylish
./app filepath1.json filepath2.json

{
  + follow: false
  + numbers: [1, 2, 3]
    setting1: Value 1
  - setting2: 200
  - setting3: true
  + setting3: {key=value}
  + setting4: blah blah
}
```

## Run
```sh
make run-dist
```

![image](https://user-images.githubusercontent.com/46719906/229506693-11010106-c22f-474a-b8c5-9b13f747d70f.png)

![image](https://user-images.githubusercontent.com/46719906/229631414-0f3acea9-7668-439d-b1db-816ca3a1c386.png)

![image](https://user-images.githubusercontent.com/46719906/229910231-f1d9a4ce-117c-4832-8b3a-030b05d76baa.png)