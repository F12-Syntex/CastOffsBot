## Commands
- /ping, checks the bots latency
- /register, registers the user to the system
- /profile, search up a users profile
- /dev, developer command to test
- /rkata, display a random kata
## Contributing
Contributions are welcome! If you have any suggestions or improvements, feel free to open an issue or submit a pull request.

## License
This project is licensed under the [MIT License](LICENSE).

Sure! Here's the updated README.md file with the feature to-do list:

## Kodo Hackathon Discord Bot

Welcome to the Kodo hackathon project! This Discord bot is being developed as part of a hackathon. Feel free to contribute to this project and add your own ideas!

## Feature To-Do List

- [x] Weekly challenges
- [x] Daily challenges
- [ ] Currency gained through challenges
- [ ] Ranking
- [ ] Time taken to beat kata
- [ ] Leaderboard
- [ ] Custom ranks
- [ ] Tournaments
- [ ] Challenge user
- [ ] Challenge backlog
- [x] Implement system to return completed katas according to a sort
- [x] Async messages, while the server loads the information, the bot will present a loading message before updating it with the content

Please feel free to add more ideas to this list or suggest improvements to existing features. Let's make this Discord bot amazing together!

## How to Contribute
Thank you for your interest in contributing to the Kodo hackathon project! Follow the steps below to get started:

1. **Fork this repository**: Click on the "Fork" button in the top-right corner of this page to create a copy of this repository under your own GitHub account.

2. **Clone the forked repository**: Clone your forked repository to your local development environment using the following command:
   ```
   git clone https://github.com/your-username/Kodo.git
   ```

3. **Set up Discord bot token**: In order to run the Discord bot locally, you'll need to set up a Discord bot and obtain its token. To do this, follow these steps:
   - Go to the [Discord Developer Portal](https://discord.com/developers/applications).
   - Create a new application and give it a name.
   - Navigate to the "Bot" tab and click on "Add Bot".
   - Under the "Token" section, click on "Copy" to copy the bot token to your clipboard.
   - Set up an environment variable named `DISCORD_TOKEN` and paste the copied token as its value. This can be done by adding the following line to your environment variables file (e.g., `.env`):
     ```
     DISCORD_TOKEN=your-bot-token
     ```

4. **Create a new branch**: Create a new branch in your local repository to work on your changes. Use a descriptive name for your branch.
   ```
   git checkout -b <branch-name>
   ```

5. **Make changes**: Make your desired changes to the codebase using your preferred text editor or IDE.

6. **Commit and push changes**: After making the necessary changes, commit your changes using the following commands:
   ```
   git add .
   git commit -m "Brief description of the changes"
   git push origin <branch-name>
   ```

7. **Create a pull request**: Once you have pushed your changes to your forked repository, navigate to the original repository (https://github.com/F12-Syntex/Kodo) on GitHub. Click on the "Compare & pull request" button to create a pull request.

8. **Review and merge the pull request**: I will review your pull request, provide feedback if needed, and then merge it if everything looks good.

That's it! You have successfully contributed to the Kodo hackathon project. Thank you for your contribution!

If you have any further questions or need additional assistance, feel free to ask. Happy coding!
