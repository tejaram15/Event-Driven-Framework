#import nodejs boron version
FROM node:boron
#Setup Working Directory
MKDIR ~/app
#Copy contents to working directory
COPY . ~/app
#Change Working Directory
WORKDIR ~/app
#install Dependicies
RUN curl https://get.dgraph.io -sSf | bash

#Run the final Related Files
RUN node HTTP_SERVER.js
RUN echo"Open your webpage at https://localhost:8080/Html/Start.html"
