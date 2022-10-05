# MyFavoriteMakes

My favorite makes
As a user, I want to see a list of vehicle makes so that I can choose which ones are my favorites.

### Design
<p float="middle">
<img width="300" alt="favorite_makes_home" src="https://user-images.githubusercontent.com/1716445/194069687-666a1f20-b51d-498d-be67-b8f2cf85309e.png">
<img width="300" alt="favorite_makes_list" src="https://user-images.githubusercontent.com/1716445/194067521-7f5f18de-6f42-470d-92f5-3b308c0bccaf.png">
</p>

### Acceptance criteria:
- App opens on a main screen
- The main screen contains a number that represents the selected favorite count.
- The main screen contains a button that when pressed opens a secondary screen to select
favorite vehicle makes
- The secondary screen displays a list of makes such as Audi, BMW, Tesla, etc.
- Each item from the list has an action button that will toggle the favorite status of the selected make.
- Returning to the main screen, displays the updated favorite count.

## Implementation hints:
- List of makes can be hardcoded for now, but in the future it will be loaded from an API. Design your
solution with this in mind.
- Favorite statuses don’t have to be persisted but they have to be kept for as long as the app is
running. In the future they will have to be persisted.

## We want to see:
- How you design the interface
- How you implement business logic
- How you test your implementation

## Architecture:
- **Hilt** as a **Dependency Injection**
- **Compose** on UI side
- **Android navigation** framework
- **Coil** dynamically fetches makes logo - it needs internet connection
- **Room** as database
- `AssetsMakesApi` simulates API call with `Favorite Makes json` — network delay is also there
- Switch to **Retrofit** call is only on **DI** layer
- Initial sync fetches `Makes models` at the first start of app - it is one time work
- Unit tests are testing domain logic

### List Performance
`LazyColumn` perfomance is not great on debug builds. I applied some Android best practices: https://stackoverflow.com/a/70596559/1888738
Fix for this issue is to generate release build. In the future **Baseline Profiles** can be introduced https://developer.android.com/topic/performance/baselineprofiles/overview
