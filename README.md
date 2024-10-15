# Infinity and Beyond
The revolutionary space-exploration plugin.

## Progress tracking
Development progress is tracked @ https://trello.com/b/wboaD0Zb

## Features
- [ ] Spaceships
  - [ ] Register building as ship
    - Instead of selecting an area and registering the building within as a ship, should there just be a build mode?
  - [ ] Fuelling
    - [ ] Consumption of fuel
    - [ ] Topping up of fuel
  - [ ] Condition (damage)
    - [ ] Repairs
    - [ ] Condition affecting ship stats (i.e. speed)
  - [ ] Upgrades
    - [ ] Speed
    - [ ] Health
    - [ ] Size
  - [ ] Track data
    - [ ] Name
    - [ ] Captain
    - [ ] Crew
    - [ ] Historical stats
      - [ ] Total distance travelled
      - [ ] Total amount of flights
      - [ ] Total flight time
      - [ ] Age
- [ ] Planetary system
  - [ ] Map of planets
  - [ ] Planets
    - [ ] Customisable world generator(s)
    - [ ] Data of planet condition
      - [ ] Gravity
      - [ ] Oxygen level
      - [ ] Resources
  - [ ] Other celestial bodies?
    - Asteroids: essentially just mini-planets useful as stops between long distances
    - Can't see a need for celestial bodies that cannot be landed on as of now
- [ ] Space travel
  - [ ] Ships to maintain bearings within planetary system
  - [ ] Calculation of flight time between celestial bodies
  - [ ] Flight mode
    - [ ] Handling of copying and breaking down of ship between planets post-flight`
- [ ] Trading system
  - [ ] Trading outposts
    - [ ] Player shops (accessible from any outpost in universe)

## Considerations
- How to handle location of ship spawning on planets?
  - How to deal with unlevel terrain?
  - How to deal with distance from other ships on the planet?

## Contributing
All pull requests are open, I'd love all the help I can get! I'm also open to new ideas and suggestions,
so please don't be shy!

## Building
```
./gradlew clean buil
```