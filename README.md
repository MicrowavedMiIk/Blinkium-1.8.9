# Blinkium 1.8.9

Blinkium is a lightweight Forge mod for 1.8.9 that facilitates "Blinking"—the art of convincing a server that you’ve finally succumbed to a fatal heart attack while your soul wanders the map in total desync.
While active, you move freely through the world while the server keeps your player model frozen in a state of rigor mortis. It is the perfect tool for analyzing PvP reach or watching your physical form get beaten to death while you stand five blocks away, powerless to stop it.

---

## Technical Details
* **Selective Packet Hijacking:** This mod **only** intercepts movement packets. Your combat and chat packets still function normally, allowing you to talk to your executioner while they’re still hitting the spot where you were standing ten seconds ago. 
* **The Mixin Nightmare:** Setting up Mixins for 1.8.9 is a special kind of hell. If the code looks like it was written by someone losing their grip on reality, that's because injecting into the Netty pipeline while Forge tries to lobotomize your IDE is a great way to waste several years of your life expectancy.

---

### Showcase
<video src="https://i.imgur.com/qzcDotd.mp4" autoplay loop muted playsinline style="max-width: 100%;"></video>

*Note: If the demo isn't loading, it's likely because I had to fight GitHub's file limits just to show you what this looks like.*

---

## Installation & Build
1. **Users:** Toss the `.jar` into your `%appdata%/.minecraft/mods` folder. Default toggle is **G**, but you should probably rebind that before you accidentally ruin your life during a recorded match.
2. **Developers:** Clone the repo and run `gradlew setupDecompWorkspace`.
3. **Build:** Run `gradlew build`. Your fresh JAR will be in `build/libs/`, assuming the configuration didn't spontaneously combust out of spite.

---

## Disclaimer & Issues
This project is for educational purposes and private server testing. If you use this on a public competitive server and get banned, do not post it in the GitHub Issues. Use your brain, or what’s left of it after setting up a 1.8.9 workspace.
**Any Issues titled "I got banned" or "Help me appeal" will be closed immediately and archived as a testament to your stupidity.** I am not your therapist, I am not your lawyer, and I certainly don't care about your "lost progress" or your "unbroken win streak." 

