# RichStringer
[![Build Status](https://travis-ci.org/chiiia12/RichStringify.svg?branch=master)](https://travis-ci.org/chiiia12/RichStringify)
RichStringer is annotation-based string generator for entity.
This is beta version.

# Usage 
Add `@ToStringLabel` to field that you want output.

```
public class Animal{
 @ToStringLabel("Animal's name")
 String name;
 @ToStringLabel("Animal's age")
 int age
}
```

To get string, Use `Stringify.toString()`
```
Animal animal = new Animal();
animal.name = "John";
animal.age = 13;
Stringify.toString(animal)
```
```ouput
Animal's name: John
Animal's age: 13
```

# Download
not prepared yet.

# License
```
Copyright 2018 Chiaki Yokoo 

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```


