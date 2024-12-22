# Scolor - color guessing game

This project uses Laminar + Scala.js. I made this project when learning Scala and also to explore its frontend dev support.

The code is messy and concerns are all over the place.

However, I can see some patterns emerging:
- I model each piece of UI + logic in a function. This function is basically a component.
- A function can call other (reusable) functions to help render part of the UI. These functions are children components.
- I often pass an AirStream `EventBus[Option[Unit]]` to children components as a way to simulate event handling in React/Vue.
- I can either pass AirStream `Signal` to children components. This is like a prop since `Signal` is read-only. To mutate `Signal`, I send an event to an `EventBus` & make the parent update it.

But I can see that there are other options:
- I can pass AirStream `Var`/`EventBus` directly to children components, which can be written directly. This simply violates the one-way dataflow pattern typically seen in React/Vue.
It's unrestricted and I'm quite confused at what to do & how to organize code properly.

The downsides:
- No HMR.
- Laminar & AirStream doc is quite hard to follow.
- Take time to get used to the whole Scala.js & AirStream model stuffs. In fact, I don't understand the concept of `Owner` when implementing this.
- Sometimes, I have to jump through hoops to achieve a piece of behavior that is more straightforward in JS.
