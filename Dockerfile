# Import nodejs boron version
FROM node:boron
# Create a new directory
RUN mkdir -p /usr/src/Event\ Driven\ Framework/
# Change Working Directory
WORKDIR /usr/src/Event\ Driven\ Framework/
# Install app dependencies
COPY package.json /usr/src/Event\ Driven\ Framework/
RUN npm install
RUN curl https://get.dgraph.io -sSf | bash
# Copy all the files
COPY . /usr/src/Event\ Driven\ Framework/
# Port expose
EXPOSE 8080
#Run the final Related Files and show output
CMD [ "node" , "npm" , "start" ]
