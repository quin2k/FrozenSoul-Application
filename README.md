# FrozenSoul-Application
Programming assignment as part of Frozen Soul Application Process

*Assignment:*

Using the Spigot API 1.9 create a plugin that meets the following criteria:

Plugin supplies its own default config file that contains a message of the day. [DONE]

Performs the six tasks below.

When the plugin is loaded:
- Build the default config (if config is not present) with a single string for message of the
  day (motd). [DONE]
- Prints the message of the day to the console via configured motd. [DONE]
- Starts a synchronous task to broadcast the message of the day every 10 minutes. [DONE]
- Plays a sound to a player when they join the server. [DONE]

When the plugin is unloaded:
- Send a sad message to console that the console is being unloaded. [DONE]
- Cancel all running tasks. [DONE]

Once you've completed the project, upload it to github and reply with a link to the project
and we will move forward to the next steps on the application. After 7 days, if the project
is not uploaded for review, the application will be declined. [DONE]

Author Note:
I tried to go above and beyond - since both the Join Message and System Messages are referred
to as "MOTD" in some contexts, I set out to do all of them.  Sadly, some of it isn't actually
functioning as intended which is why it is commented out.  I do have some additional features
such as mhelp, mreload, motd, and joinmotd commands.
