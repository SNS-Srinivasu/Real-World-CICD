sudoapt-getupdate
sudo apt-get install docker.io -У
sudo usermod -aG docker ubuntu
newgrp docker 
sudo chmod 777 /var/run/docker.sock
docker ps


