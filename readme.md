Deploy on Heroku:

The project is structured to build all modules in a single jar file

The file Procfile in the root directory of the project tell heroku how to start the project

git push -f heroku deploy-heroku:main

The above command push the local branch deploy-heroku to the heroku remote branch main

heroku logs --tail

Shows log details of the build and status of the process