#import nodejs boron version
FROM node:boron
#Copy contents to working directory
COPY . /usr/src
#Change Working Directory
WORKDIR /usr/src/Event Driven Framework/
#install Dependicies
RUN curl https://get.dgraph.io -sSf | bash

#Run the final Related Files and show output
RUN node HTTP_SERVER.js
RUN echo"Open your webpage at https://localhost:8080/Html/Start.html"
