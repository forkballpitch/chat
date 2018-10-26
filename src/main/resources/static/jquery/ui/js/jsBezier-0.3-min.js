(function(){if("undefined"==typeof Math.sgn)Math.sgn=function(a){return 0==a?0:0<a?1:-1};var l={subtract:function(a,b){return{x:a.x-b.x,y:a.y-b.y}},dotProduct:function(a,b){return a.x*b.x+a.y*b.y},square:function(a){return Math.sqrt(a.x*a.x+a.y*a.y)},scale:function(a,b){return{x:a.x*b,y:a.y*b}}},w=Math.pow(2,-65),u=function(a,b){for(var f=[],d=b.length-1,h=2*d-1,g=[],c=[],k=[],i=[],m=[[1,0.6,0.3,0.1],[0.4,0.6,0.6,0.4],[0.1,0.3,0.6,1]],e=0;e<=d;e++)g[e]=l.subtract(b[e],a);for(e=0;e<=d-1;e++)c[e]=l.subtract(b[e+
1],b[e]),c[e]=l.scale(c[e],3);for(e=0;e<=d-1;e++)for(var n=0;n<=d;n++)k[e]||(k[e]=[]),k[e][n]=l.dotProduct(c[e],g[n]);for(e=0;e<=h;e++)i[e]||(i[e]=[]),i[e].y=0,i[e].x=parseFloat(e)/h;h=d-1;for(g=0;g<=d+h;g++){e=Math.max(0,g-h);for(c=Math.min(g,d);e<=c;e++)j=g-e,i[e+j].y+=k[j][e]*m[j][e]}d=b.length-1;i=q(i,2*d-1,f,0);h=l.subtract(a,b[0]);k=l.square(h);for(e=m=0;e<i;e++)h=l.subtract(a,r(b,d,f[e],null,null)),h=l.square(h),h<k&&(k=h,m=f[e]);h=l.subtract(a,b[d]);h=l.square(h);h<k&&(k=h,m=1);return{location:m,
distance:k}},q=function(a,b,f,d){var h=[],g=[],c=[],k=[],i=0,m,e;e=Math.sgn(a[0].y);for(var n=1;n<=b;n++)m=Math.sgn(a[n].y),m!=e&&i++,e=m;switch(i){case 0:return 0;case 1:if(64<=d)return f[0]=(a[0].x+a[b].x)/2,1;var o,i=a[0].y-a[b].y;m=a[b].x-a[0].x;e=a[0].x*a[b].y-a[b].x*a[0].y;n=max_distance_below=0;for(o=1;o<b;o++){var l=i*a[o].x+m*a[o].y+e;l>n?n=l:l<max_distance_below&&(max_distance_below=l)}o=m;n=(1*(e-n)-0*o)*(1/(0*o-1*i));o=m;i=(1*(e-max_distance_below)-0*o)*(1/(0*o-1*i));m=Math.min(n,i);if(Math.max(n,
i)-m<w)return c=a[b].x-a[0].x,k=a[b].y-a[0].y,f[0]=0+1*(c*(a[0].y-0)-k*(a[0].x-0))*(1/(0*c-1*k)),1}r(a,b,0.5,h,g);a=q(h,b,c,d+1);b=q(g,b,k,d+1);for(d=0;d<a;d++)f[d]=c[d];for(d=0;d<b;d++)f[d+a]=k[d];return a+b},r=function(a,b,f,d,h){for(var g=[[]],c=0;c<=b;c++)g[0][c]=a[c];for(a=1;a<=b;a++)for(c=0;c<=b-a;c++)g[a]||(g[a]=[]),g[a][c]||(g[a][c]={}),g[a][c].x=(1-f)*g[a-1][c].x+f*g[a-1][c+1].x,g[a][c].y=(1-f)*g[a-1][c].y+f*g[a-1][c+1].y;if(null!=d)for(c=0;c<=b;c++)d[c]=g[c][0];if(null!=h)for(c=0;c<=b;c++)h[c]=
g[b-c][c];return g[b][0]},v={},x=function(a){var b=v[a];if(!b){var b=[],f=function(a){return function(){return a}},d=function(){return function(a){return a}},h=function(){return function(a){return 1-a}},g=function(a){return function(b){for(var c=1,d=0;d<a.length;d++)c*=a[d](b);return c}};b.push(new function(){return function(b){return Math.pow(b,a)}});for(var c=1;c<a;c++){for(var k=[new f(a)],i=0;i<a-c;i++)k.push(new d);for(i=0;i<c;i++)k.push(new h);b.push(new g(k))}b.push(new function(){return function(b){return Math.pow(1-
b,a)}});v[a]=b}return b},p=function(a,b){for(var f=x(a.length-1),d=0,h=0,g=0;g<a.length;g++)d+=a[g].x*f[g](b),h+=a[g].y*f[g](b);return{x:d,y:h}},s=function(a,b,f){for(var d=p(a,b),h=0,g=0<f?1:-1,c=null;h<Math.abs(f);)b+=0.005*g,c=p(a,b),h+=Math.sqrt(Math.pow(c.x-d.x,2)+Math.pow(c.y-d.y,2)),d=c;return{point:c,location:b}},t=function(a,b){var f=p(a,b),d=p(a.slice(0,a.length-1),b),h=d.y-f.y,f=d.x-f.x;return 0==h?Infinity:Math.atan(h/f)};window.jsBezier={distanceFromCurve:u,gradientAtPoint:t,gradientAtPointAlongCurveFrom:function(a,
b,f){b=s(a,b,f);if(1<b.location)b.location=1;if(0>b.location)b.location=0;return t(a,b.location)},nearestPointOnCurve:function(a,b){var f=u(a,b);return{point:r(b,b.length-1,f.location,null,null),location:f.location}},pointOnCurve:p,pointAlongCurveFrom:function(a,b,f){return s(a,b,f).point},perpendicularToCurveAt:function(a,b,f,d){b=s(a,b,null==d?0:d);a=t(a,b.location);d=Math.atan(-1/a);a=f/2*Math.sin(d);f=f/2*Math.cos(d);return[{x:b.point.x+f,y:b.point.y+a},{x:b.point.x-f,y:b.point.y-a}]}}})();