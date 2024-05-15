Vagrant.configure("2") do |config|
  config.vm.box = "generic/centos9s"

  if Vagrant.has_plugin?("vagrant-vbguest")
    config.vbguest.auto_update = false
  end

  config.vm.provider "virtualbox" do |v|
    v.memory = 20480
    v.cpus = 4
    v.gui = true
  end

  config.vm.define "mtsa" do |k8s|
    k8s.vm.hostname = "mtsa"
  end

  config.vm.network "forwarded_port", guest: 5005, host: 5005  # Remote JVM Debug
  config.vm.network "private_network", ip: "192.168.56.10"
  config.ssh.forward_x11 = true

  config.vm.synced_folder "./bin", "/home/vagrant/mtsa/bin"
  config.vm.synced_folder "./maven-root/mtsa", "/home/vagrant/mtsa/mtsa"
end
