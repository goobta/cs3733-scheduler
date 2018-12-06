FROM node:11-stretch

RUN npm install --global yarn

COPY package.json .
RUN npm install

WORKDIR /app

CMD yarn serve
