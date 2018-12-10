FROM node:11-stretch

COPY package.json .
RUN npm install

WORKDIR /app

CMD cp -r /node_modules /app/ && cp /package-lock.json /app && yarn serve
