FROM centos/python-27-centos7 

ENV LD_LIBRARY_PATH=/opt/rh/python27/root/usr/lib64

RUN yum -y install epel-release \
    && yum -y install python-devel \
    && yum -y install python-pip \
	&& pip install requests

USER root

COPY . /app

CMD "python ./sessions.py"
