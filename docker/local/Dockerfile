FROM postgres:13

ENV LANG de_DE.utf8
ENV POSTGRES_USER 'postgres'
ENV POSTGRES_PASSWORD 'postgres'
ENV POSTGRES_HOST_AUTH_METHOD trust

RUN set -x && \
    localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8