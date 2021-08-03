# speech2text - PoC

Current application was made to prove a concept of voice recognition after some certain keyboard button has 
pressed. Once it's pressed, user can start talking whatever he wants to. Voice recognition
begins working after button is released.

Ideally voice recogniser should take care of all gotten speech by audio input. However,
it works according to grammar file that can be recognized as an artificial vocabulary.

## Project structure

Consists of couple components:
1) sphinx4 - voice recognition library
2) jnativehook - keyboard listener

## How to use
Simply add all words/phrases you are going to use into sgrammar file (resources/sphinx folder) and 
run the application. Hold left ctrl button and start talking those phrases from gram file. 
Results can be found in console output.

## Links
Sphinx off. - https://github.com/cmusphinx </br>
Jnativehook off. - https://github.com/kwhat/jnativehook