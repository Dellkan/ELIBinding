WORK IN PROGRESS
========
Excuse the mess.

(This project still has a long way to go, and doesn't work as described just yet. Stay tuned.)

Purpose
========
ELIBinding is a library for databinding, much like Robobinding or Google's own databinding. 
It will allow you to attach arbitrary data-models onto the layout-inflation process, and refer to it's fields and 
attributes directly in the layout file. There's utility to automatically refresh/update both model and view based on 
changes in the other.

ELIBinding uses https://github.com/Dellkan/EnhancedLayoutInflater internally.

Setup
========
TODO

Weirdness, caveats and other peculiar behaviours
========
TODO

Todo:
========
 - [x] Basic one-way binding (model->view)
 - [x] Basic event binding (view.onClick->model)
 - [x] Basic two-way binging (view->model)
 - [x] Basic multi-binding on single view (grouped binding)
 - [ ] Add support for ListView/Recycler bindings
 - [ ] Add bindings for common attributes
 
Similar libraries
========
 - https://github.com/RoboBinding/RoboBinding (ELIBinding is inspired by Robobinding - 
 Robobinding is currently much more stable, feature-rich and battle-tested)
